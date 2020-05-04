import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, share } from 'rxjs/operators';

@Injectable()
export class HttpService<T> {
  public static claseGenerica: any = null;

  constructor(private http: HttpClient) {}

  static setClass(clase: any) {
    this.claseGenerica = clase;
  }

  public get(url: string, params: HttpParams = null): Observable<T> {
    let options: any = {};
    if (params) {
      options = {
        params: params
      };
    }
    let clase: any = HttpService.claseGenerica;

    let observable: any = this.http
      .get(url, options)
      .pipe(map((res: any) => res))
      .pipe(share())
      .pipe(
        map((posts: any) => {
          //asumiendo que todos los servicios get traen data en el objeto y es un array
          let array = posts;
          if (!Array.isArray(array)) {
            array = posts.data;
          }
          let obj = array.map(item => clase.fromJson(item));

          return obj;
        })
      );
    observable.subscribe(() => {}, () => {});
    return observable;
  }

  public post(url: string, data: any = null, headers: any = null): Observable<T> {
    let dataSend: any = {};
    let options: any = {};
    if (data) {
      dataSend = data;
    }

    if (headers) {
      options.headers = headers;
    }
    let clase: any = HttpService.claseGenerica;

    let observable: any = this.http
      .post(url, dataSend, options)
      .pipe(share()) //permite solo una peticion por llamada a pesar de invocar varias veces el subscribe
      .pipe(map((res: any) => res));
    /**Parsear a objeto en caso de que los POST funcionen como deberían */
    /*.pipe(map((posts: Object[]) => {
            return posts.map(item => clase.fromJson(item));
        }));*/
    observable.subscribe(() => {}, () => {});
    return observable;
  }

  public put(url: string, data: any = null): Observable<T> {
    let dataSend: any = {};
    if (data) {
      dataSend = data;
    }
    let clase: any = HttpService.claseGenerica;

    let observable: any = this.http
      .put(url, dataSend)
      .pipe(share()) //permite solo una peticion por llamada a pesar de invocar varias veces el subscribe
      .pipe(map((res: any) => res));
    /**Parsear a objeto en caso de que los POST funcionen como deberían */
    /*.pipe(map((posts: Object[]) => {
            return posts.map(item => clase.fromJson(item));
        }));*/
    observable.subscribe(() => {}, () => {});
    return observable;
  }

  public delete(url: string, data: any = null): Observable<T> {
    let dataSend: any = {};
    if (data) {
      dataSend = data;
    }
    let clase: any = HttpService.claseGenerica;

    let observable: any = this.http
      .delete(url, dataSend)
      .pipe(share()) //permite solo una peticion por llamada a pesar de invocar varias veces el subscribe
      .pipe(map((res: any) => res));
    /**Parsear a objeto en caso de que los POST funcionen como deberían */
    /*.pipe(map((posts: Object[]) => {
            return posts.map(item => clase.fromJson(item));
        }));*/
    observable.subscribe(() => {}, () => {});
    return observable;
  }
}
