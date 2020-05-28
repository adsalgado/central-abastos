import { JhiEventManager } from 'ng-jhipster';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';

import { SERVER_API_URL } from 'app/app.constants';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';

@Injectable()
export class AuthServerProvider {
  constructor(
    private http: HttpClient,
    private $localStorage: LocalStorageService,
    private $sessionStorage: SessionStorageService,
    private localStorageEncryptService: LocalStorageEncryptService,
    private events: JhiEventManager
  ) {}

  getToken() {
    return this.$localStorage.retrieve('authenticationToken') || this.$sessionStorage.retrieve('authenticationToken');
  }

  login(credentials): Observable<any> {
    const data = {
      username: credentials.username,
      password: credentials.password,
      rememberMe: credentials.rememberMe
    };
    let component: any = this;
    console.log(component);
    return this.http.post(SERVER_API_URL + 'api/authenticate', data, { observe: 'response' }).pipe(map(authenticateSuccess.bind(this)));

    function authenticateSuccess(resp) {
      //console.log(resp.body);
      component.localStorageEncryptService.setToLocalStorage('userSession', resp.body);
      component.events.broadcast({ name: 'armaMenu', content: {} });

      //this.events.broadcast({ name: 'updateProductos', content: productoDelete });
      const bearerToken = resp.headers.get('Authorization');
      if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
        const jwt = bearerToken.slice(7, bearerToken.length);
        this.storeAuthenticationToken(jwt, credentials.rememberMe);
        return jwt;
      }
    }
  }

  loginWithToken(jwt, rememberMe) {
    if (jwt) {
      this.storeAuthenticationToken(jwt, rememberMe);
      return Promise.resolve(jwt);
    } else {
      return Promise.reject('auth-jwt-service Promise reject'); // Put appropriate error message here
    }
  }

  storeAuthenticationToken(jwt, rememberMe) {
    if (rememberMe) {
      this.$localStorage.store('authenticationToken', jwt);
    } else {
      this.$sessionStorage.store('authenticationToken', jwt);
    }
  }

  logout(): Observable<any> {
    return new Observable(observer => {
      this.$localStorage.clear('authenticationToken');
      this.$sessionStorage.clear('authenticationToken');
      observer.complete();
    });
  }
}
