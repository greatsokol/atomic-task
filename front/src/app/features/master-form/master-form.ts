import {Component, inject, OnInit} from '@angular/core';
import {MasterService} from '../../data/services/master.service';
import {AbstractControl, FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {DetailsResponseDto} from '../../data/interfaces';
import {MatButtonModule} from '@angular/material/button';
import Big from 'big.js';
import {CustomValidators} from '../../validators';
import {firstValueFrom} from 'rxjs';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {MatDivider} from '@angular/material/divider';
import {DetailService} from '../../data/services/detail.service';


@Component({
  selector: 'app-master-form',
  imports: [
    ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatDivider, RouterLink
  ],
  templateUrl: './master-form.html',
  styleUrl: './master-form.scss',
})
export class MasterForm implements OnInit {
  #httpMaster = inject(MasterService);
  #httpDetails = inject(DetailService);
  #fb = inject(FormBuilder);
  #ar = inject(ActivatedRoute);
  id?: string;

  form: FormGroup = this.#fb.group({
    id: this.#fb.control<string | null>({value: null, disabled: true}),
    number: this.#fb.control<number | null>(null, {validators: Validators.required}),
    description: this.#fb.control<string | null>(null, {validators: [Validators.maxLength(255), Validators.required]}),
    total: this.#fb.control<string | null>({value: null, disabled: true}),
    details: this.#fb.array<DetailsResponseDto>([]),
  });

  isAddMode() {
    return !this.id;
  }

  isEditMode() {
    return this.id;
  }

  ngOnInit() {
    this.details.valueChanges.subscribe(details => this.calculateTotal(details));
    this.#ar.params.subscribe(params => {
      this.id = params['id'];
      if (this.id) {
        this.loadMaster();
      }
    });
  }

  private calculateTotal(details: DetailsResponseDto[]) {
    let total = Big(0);
    details.forEach(c => {
      try {
        const price = this.roundBig(c.price);
        total = total.add(price);
      } catch (e) {
      }
    });
    this.form.patchValue({'total': total.toString()});
  }

  get details() {
    return this.form.get('details') as FormArray;
  }

  protected detailSaveDisabledAt(i: number) {
    const detail = this.details.at(i);
    return detail.invalid || !detail.dirty;
  }

  protected addDetail() {
    this.details.push(this.#fb.group({
      id: this.#fb.control<string | null>({value: null, disabled: true}),
      name: this.#fb.control<string | null>(null, {
        validators: [Validators.maxLength(255), Validators.required],
        updateOn: "blur"
      }),
      price: this.#fb.control<string | null>(null, {
        validators: [Validators.required, CustomValidators.bigDecimalValid]
      })
    }));
  }

  protected removeDetailAt(i: number) {
    const detail = this.details.at(i);
    const detailId = detail.getRawValue().id;
    debugger;
    if (detailId) {
      // деталь уже сохранена, удаляем на сервере и из структуры
      this.deleteDetail(detailId).then(_ => this.details.removeAt(i));
    } else {
      // деталь еще не сохранена, удаляем только из структуры
      this.details.removeAt(i);
    }
  }

  protected saveDetailAt(i: number) {
    const detail = this.details.at(i);
    this.saveDetail(detail, true)
  }

  protected onSubmit() {
    if (this.form.disabled || this.form.invalid) return;
    this.form.markAllAsTouched();
    this.form.updateValueAndValidity();
    console.debug('this.form.value', this.form.value);

    if (this.isAddMode()) {
      this.postMaster();
    } else {
      this.putMaster();
    }
  }

  private loadMaster() {
    firstValueFrom(this.#httpMaster.getMaster(this.id!))
      .then(value => {
        value.details.forEach(_ => this.addDetail());
        this.form.patchValue(value);
      })
  }

  protected postMaster() {
    firstValueFrom(this.#httpMaster.postMaster(this.form.value))
      .then(value => this.form.reset(value));
  }

  protected putMaster() {
    const value = this.form.value;
    delete value['details'];

    firstValueFrom(this.#httpMaster.putMaster(this.id!, value)).then(
      _ => {
        this.saveAllDirtyDetails();
        this.form.reset(this.form.getRawValue());
      }
    )
  }

  protected saveDetail(detail: AbstractControl, showSuccess: boolean) {
    const value = detail.getRawValue();
    value.price = this.roundBig(value.price).toString();

    if (!value['id']) {
      // деталь еще не сохранена, сохраняем через post-запрос
      firstValueFrom(this.#httpDetails.postDetail(this.id!, value, showSuccess))
        .then(data => detail.reset(data));
    } else {
      // деталь уже сохранена, сохраняем через put-запрос
      firstValueFrom(this.#httpDetails.putDetail(this.id!, value, showSuccess))
        .then(data => detail.reset(data)
    )
      ;
    }
  }

  protected saveAllDirtyDetails() {
    this.details.controls.forEach(detailControl => {
      if (detailControl.dirty && detailControl.valid) {
        this.saveDetail(detailControl, false);
      }
    });
  }

  protected deleteDetail(detailId: string) {
    return firstValueFrom(this.#httpDetails.deleteDetail(this.id!, detailId))
      .then();
  }

  private roundBig(value: string) {
    return Big(value).round(2, Big.roundDown);
  }
}
