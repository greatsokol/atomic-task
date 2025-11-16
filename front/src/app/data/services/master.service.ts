import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BACKEND_SERVER_SETTINGS} from '../../tokens';
import {MasterPostDto, MasterPutDto, MasterResponseDtoInterface} from '../interfaces';
import {Observable} from 'rxjs';
import {HttpBaseApiServiceService} from './http-base-api-service.service';

@Injectable({
  providedIn: 'root',
})
export class MasterService extends HttpBaseApiServiceService {
  #http = inject(HttpClient);
  #apiUrl = inject(BACKEND_SERVER_SETTINGS).uri;
  #masterPath = '/api/v1/master';

  getMasterAll(): Observable<MasterResponseDtoInterface[]> {
    return this.with(
      this.#http.get<MasterResponseDtoInterface[]>(`${this.#apiUrl}${this.#masterPath}`)
    );
  }

  getMaster(id: string): Observable<MasterResponseDtoInterface> {
    return this.with(
      this.#http.get<MasterResponseDtoInterface>(`${this.#apiUrl}${this.#masterPath}/${id}`)
    );
  }

  postMaster(data: MasterPostDto): Observable<MasterResponseDtoInterface> {
    return this.with(
      this.#http.post<MasterResponseDtoInterface>(`${this.#apiUrl}${this.#masterPath}`, data),
      'Успешно'
    );
  }

  putMaster(id: string, data: MasterPutDto): Observable<MasterResponseDtoInterface> {
    return this.with(
      this.#http.put<MasterResponseDtoInterface>(`${this.#apiUrl}${this.#masterPath}/${id}`, data),
      'Успешно'
    );
  }

  deleteMaster(id: string): Observable<void> {
    return this.with(
      this.#http.delete<MasterResponseDtoInterface>(`${this.#apiUrl}${this.#masterPath}/${id}`),
      'Успешно'
    );
  }
}
