import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { Injectable, Inject } from '@angular/core';
import 'rxjs/add/operator/timeout';
import { catchError, timeout } from 'rxjs/operators';
import { of } from 'rxjs/observable/of';
import { Observable, TimeoutError } from 'rxjs';
import { map } from 'rxjs/operators';
import { LocalStorageEncryptService } from './local-storage-encrypt-service';

export const TIME_OUT = 1000 * 60 * 1; //ultimo número define en minutos
/**Clase provider que es básicamente un servicio generico para las peticiones a servicios */
@Injectable()
export class GenericService {
  public user: any = null;
  constructor(private readonly http: HttpClient, private localStorageEncryptService: LocalStorageEncryptService) {
    this.user = this.localStorageEncryptService.getFromLocalStorage('userSession');
  }

  /**Método que hace peticiones tipo GET */
  sendGetRequest(webservice_URL: string, clase: any = null) {
    let observable: any = this.http.get(webservice_URL);

    if (clase) {
      return observable.pipe(
        map((data: any) => {
          let arr: any = data;

          let obj: any = null;
          if (!Array.isArray(arr)) {
            obj = clase.fromJson(arr);
          } else {
            obj = arr.map(item => clase.fromJson(item));
          }
          return obj;
        })
      );
    } else {
      return observable;
    }
  }

  /**Método que hace peticiones tipo GET  con parámetros*/
  sendGetRequestParams(webservice_URL: string, params: any) {
    //return this.http.get(webservice_URL, params).timeout(TIME_OUT);
    return this.http.get(webservice_URL, params);
  }

  imgProblema() {
    let color: any = this.localStorageEncryptService.getFromLocalStorage('theme');
    let armado: string = '../../../content/imgs/problemas/problema';
    let retornar: any =
      color == '#3b64c0'
        ? `${armado}2.png`
        : color == '#be3b3b'
        ? `${armado}3.png`
        : color == '#3bb8be'
        ? `${armado}4.png`
        : color == '#292929'
        ? `${armado}6.png`
        : color == '#F07C1B'
        ? `${armado}1.png`
        : `${armado}5.png`;

    return retornar;
  }

  /**Método que hace peticiones tipo GET  con parámetros*/
  sendGetParams(webservice_URL: string, params: any) {
    //return this.http.get(webservice_URL, params).timeout(TIME_OUT);
    let options: any = {};
    options.params = params;
    return this.http.get(webservice_URL, options);
  }

  /**Método que hace peticiones tipo POST  con parámetros específicos*/
  sendPostRequestParams(webservice_URL: string, params: any, httpOptions: any) {
    //return this.http.post(webservice_URL, params, httpOptions).timeout(TIME_OUT);
    return this.http.post(webservice_URL, params, httpOptions);
  }

  /**Método que hace peticiones tipo POST */
  sendPostRequest(webservice_URL: string, request: {}) {
    //return this.http.post(webservice_URL, request).timeout(TIME_OUT);
    return this.http.post(webservice_URL, request);
  }

  /**Método que hace peticiones tipo PUT */
  sendPutRequest(webservice_URL: string, request: {} = {}) {
    //return this.http.post(webservice_URL, request).timeout(TIME_OUT);
    return this.http.put(webservice_URL, request);
  }

  /**Método que hace peticiones tipo DELETE */
  sendDeleteRequest(webservice_URL: string) {
    //return this.http.delete(webservice_URL).timeout(TIME_OUT);
    return this.http.delete(webservice_URL);
  }

  /**Método que hace peticiones tipo DELETE */
  sendDelete(webservice_URL: string) {
    //return this.http.delete(webservice_URL).timeout(TIME_OUT);
    return this.http.delete(webservice_URL);
  }

  getTotalCarrito() {
    if (this.user) {
      let productosCarrito: any = this.localStorageEncryptService.getFromLocalStorage(`${this.user.id_token}`);
      if (productosCarrito) {
        return productosCarrito.length;
      } else {
        return 0;
      }
    } else {
      return 0;
    }
  }

  getColor() {
    let color: any = this.localStorageEncryptService.getFromLocalStorage('theme');
    let retornar: any =
      color == '#3b64c0'
        ? 'primary'
        : color == '#be3b3b'
        ? 'primary2'
        : color == '#3bb8be'
        ? 'primary3'
        : color == '#292929'
        ? 'primary5'
        : 'primary4';
    //console.log(retornar);
    return retornar;
  }

  getColorHex() {
    let color: any = this.localStorageEncryptService.getFromLocalStorage('theme');

    return color ? color : '#f07c1c';
  }

  getColorClass() {
    let color: any = this.localStorageEncryptService.getFromLocalStorage('theme');
    let retornar: any =
      color == '#3b64c0'
        ? 'alerta-loteria'
        : color == '#be3b3b'
        ? 'alerta-loteria2'
        : color == '#3bb8be'
        ? 'alerta-loteria3'
        : color == '#292929'
        ? 'alerta-loteria5'
        : 'alerta-loteria4';
    //console.log(retornar);
    return retornar;
  }

  getColorClassTWO() {
    let color: any = this.localStorageEncryptService.getFromLocalStorage('theme');
    let retornar: any =
      color == '#3b64c0'
        ? 'alerta-two-button'
        : color == '#be3b3b'
        ? 'alerta-two-button2'
        : color == '#3bb8be'
        ? 'alerta-two-button3'
        : color == '#292929'
        ? 'alerta-two-button5'
        : 'alerta-two-button4';
    //console.log(retornar);
    return retornar;
  }

  imgLogin() {
    let color: any = this.localStorageEncryptService.getFromLocalStorage('theme');
    let armado: string = 'assets/imgs/login/loginFondo';
    let retornar: any =
      color == '#3b64c0'
        ? `${armado}.png`
        : color == '#be3b3b'
        ? `${armado}2.png`
        : color == '#3bb8be'
        ? `${armado}3.png`
        : color == '#292929'
        ? `${armado}5.png`
        : `${armado}4.png`;
    //console.log(retornar);
    return retornar;
  }

  getUser() {
    return this.localStorageEncryptService.getFromLocalStorage('userSession');
  }
}
