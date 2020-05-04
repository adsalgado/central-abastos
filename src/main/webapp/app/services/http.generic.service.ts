import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, share } from 'rxjs/operators';

@Injectable()
export class HttpServiceGeneric {
  constructor(private http: HttpClient) {}

  public get(url: string, params: HttpParams = null) {
    let options: any = {};
    if (params) {
      options = {
        params: params
      };
    }
    let observable: any = this.http.get(url, options);
    observable.subscribe(() => {}, () => {});
    return observable;
  }

  public post(url: string, data: any = null, headers: any = null) {
    let dataSend: any = {};
    let options: any = {};
    if (data) {
      dataSend = data;
    }

    if (headers) {
      options.headers = headers;
    }

    let observable: any = this.http.post(url, dataSend, options);
    /* .pipe(share())//permite solo una peticion por llamada a pesar de invocar varias veces el subscribe
            .pipe(map((res: any) => res)) */
    /**Parsear a objeto en caso de que los POST funcionen como deberían */
    /*.pipe(map((posts: Object[]) => {
            return posts.map(item => clase.fromJson(item));
        }));*/
    observable.subscribe(() => {}, () => {});
    return observable;
  }

  public put(url: string, data: any = null) {
    let dataSend: any = {};
    if (data) {
      dataSend = data;
    }

    let observable: any = this.http.put(url, dataSend);
    /**Parsear a objeto en caso de que los POST funcionen como deberían */
    /*.pipe(map((posts: Object[]) => {
            return posts.map(item => clase.fromJson(item));
        }));*/
    observable.subscribe(() => {}, () => {});
    return observable;
  }

  public delete(url: string, data: any = null) {
    let dataSend: any = {};
    if (data) {
      dataSend = data;
    }

    let observable: any = this.http.delete(url, dataSend);
    /**Parsear a objeto en caso de que los POST funcionen como deberían */
    /*.pipe(map((posts: Object[]) => {
            return posts.map(item => clase.fromJson(item));
        }));*/
    observable.subscribe(() => {}, () => {});
    return observable;
  }
}
