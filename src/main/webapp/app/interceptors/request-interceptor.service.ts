import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
import { HttpHandler, HttpInterceptor, HttpRequest, HttpErrorResponse, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/catch';
import { Observable } from 'rxjs/Observable';
import { TimeoutError, BehaviorSubject } from 'rxjs';
import { catchError, switchMap, finalize, filter, take } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';
import { JhiEventManager } from 'ng-jhipster';

/**Clase provider que intercepta las llamadas o peticiones a los servicios back-end y en caso de que el usuario
 * se encuentre en sesión añade los header de token
 */
@Injectable()
export class RequestInterceptorService implements HttpInterceptor {
  constructor(
    public auth: AuthService,
    private eventManager: JhiEventManager,
    private localStorageEncryptService: LocalStorageEncryptService
  ) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let chequeo: any = this.auth.getToken();

    let headers: any = {
      'Content-Type': 'application/json'
    };
    if (chequeo && request.url != 'https://maps.googleapis.com/maps/api/geocode/json') {
      headers.Authorization = `Bearer ${this.auth.getToken()}`;
    }
    /* let urlParse: any = request.url.split("api");
    switch (urlParse[1]) {
      case "promociones":

        break;

      default:
        headers.Authorization = `Bearer ${this.auth.getToken()}`;
        break;
    } */
    let user: any = this.localStorageEncryptService.getFromLocalStorage('userSession');
    if (
      request.url == 'https://maps.googleapis.com/maps/api/geocode/json' ||
      (!user && request.url == 'https://dev-cabasto.sharktech.com.mx/api/notificaciones')
    ) {
      this.eventManager.broadcast({ name: 'intervalando', content: { a: 1 } });
      return next.handle(request);
    } else {
      request = request.clone({
        setHeaders: headers
      });

      return next.handle(request).pipe(
        catchError((errorResponse: HttpErrorResponse) => {
          let error: any = null;
          try {
            error = typeof errorResponse !== 'object' ? JSON.parse(errorResponse) : errorResponse;
          } catch (error) {
            error = errorResponse;
          }
          if (
            (error && error.status == 401 && !request.url.toString().includes('api/authenticate') && error.error.title == 'Unauthorized') ||
            error.error.title == 'El cliente es requerido'
          ) {
            //this.events.publish("startSession");

            this.eventManager.broadcast({ name: 'startSession', content: { a: 1 } });
            return Observable.throw(error);
          } else {
            return next.handle(request);
          }
        })
      );
    }
  }
}
