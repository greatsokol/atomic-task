import {inject, Injectable} from '@angular/core';
import {catchError, Observable, tap, throwError} from 'rxjs';
import {NotificationService} from '../../services/notification.service';
import {HttpErrorResponse} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HttpBaseApiServiceService {
  #notification = inject(NotificationService);

  withNotification(request: Observable<any>, successMessage?: string) {
    return request.pipe(
      tap(_ => {
        if (successMessage) this.#notification.notify(successMessage)
      }),
      catchError((e: HttpErrorResponse) => {
        this.#notification.notify(e.error.message || e.message);
        return throwError(() => e);
      })
    );
  }

  with(request: Observable<any>, successMessage?: string) {
    return this.withNotification(request, successMessage);
  }
}
