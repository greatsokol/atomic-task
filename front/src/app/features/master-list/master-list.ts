import {Component, inject, OnInit, ViewChild} from '@angular/core';
import {MasterService} from '../../data/services/master.service';
import {firstValueFrom} from 'rxjs';
import {MasterResponseDtoInterface} from '../../data/interfaces';
import {MatTable, MatTableDataSource, MatTableModule} from '@angular/material/table';
import {RouterLink} from '@angular/router';
import {MatButtonModule} from '@angular/material/button';

@Component({
  selector: 'app-master-list',
  imports: [
    MatTableModule,
    MatButtonModule,
    RouterLink
  ],
  templateUrl: './master-list.html',
  styleUrl: './master-list.scss',
})
export class MasterList implements OnInit {
  #service = inject(MasterService);
  dataSource = new MatTableDataSource<MasterResponseDtoInterface>();
  displayedColumns = ['number', 'description', 'total', 'buttons'];

  ngOnInit() {
    // this.masterRecords$ = this.#service.getMasterAll();
    this.#service.getMasterAll().subscribe(data => this.dataSource.data = data);
  }

  protected deleteMaster(id: string) {
    firstValueFrom(this.#service.deleteMaster(id)).then(_ => this.removeFromDataSource(id));
  }

  private removeFromDataSource(id: string) {
    this.dataSource.data = this.dataSource.data.filter(e => e.id != id);
  }
}
