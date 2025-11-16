import {inject, Injectable} from '@angular/core';
import {HttpBaseApiServiceService} from './http-base-api-service.service';
import {HttpClient} from '@angular/common/http';
import {BACKEND_SERVER_SETTINGS} from '../../tokens';
import {DetailsPostDto, DetailsPutDto, DetailsResponseDto} from '../interfaces';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DetailService extends HttpBaseApiServiceService {
  #http = inject(HttpClient);
  #apiUrl = inject(BACKEND_SERVER_SETTINGS).uri;
  #masterPath = '/api/v1/master';

  postDetail(masterId: string, data: DetailsPostDto, showSuccess?: boolean): Observable<DetailsResponseDto> {
    return this.with(
      this.#http.post<DetailsPostDto>(`${this.#apiUrl}${this.#masterPath}/${masterId}/detail`, data),
      showSuccess ? 'Успешно' : undefined
    );
  }

  putDetail(masterId: string, data: DetailsPutDto, showSuccess?: boolean): Observable<DetailsResponseDto> {
    return this.with(
      this.#http.put<DetailsPutDto>(`${this.#apiUrl}${this.#masterPath}/${masterId}/detail/${data.id}`, data),
      showSuccess ? 'Успешно' : undefined
    );
  }

  deleteDetail(masterId: string, detailId: string): Observable<void> {
    return this.with(
      this.#http.delete<DetailsResponseDto>(`${this.#apiUrl}${this.#masterPath}/${masterId}/detail/${detailId}`),
      'Успешно'
    );
  }
}
