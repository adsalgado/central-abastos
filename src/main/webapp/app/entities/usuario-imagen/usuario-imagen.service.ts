import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUsuarioImagen } from 'app/shared/model/usuario-imagen.model';

type EntityResponseType = HttpResponse<IUsuarioImagen>;
type EntityArrayResponseType = HttpResponse<IUsuarioImagen[]>;

@Injectable()
export class UsuarioImagenService {
  public resourceUrl = SERVER_API_URL + 'api/usuario-imagens';

  constructor(protected http: HttpClient) {}

  create(usuarioImagen: IUsuarioImagen): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(usuarioImagen);
    return this.http
      .post<IUsuarioImagen>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(usuarioImagen: IUsuarioImagen): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(usuarioImagen);
    return this.http
      .put<IUsuarioImagen>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUsuarioImagen>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUsuarioImagen[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(usuarioImagen: IUsuarioImagen): IUsuarioImagen {
    const copy: IUsuarioImagen = Object.assign({}, usuarioImagen, {
      fechaAlta: usuarioImagen.fechaAlta != null && usuarioImagen.fechaAlta.isValid() ? usuarioImagen.fechaAlta.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaAlta = res.body.fechaAlta != null ? moment(res.body.fechaAlta) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((usuarioImagen: IUsuarioImagen) => {
        usuarioImagen.fechaAlta = usuarioImagen.fechaAlta != null ? moment(usuarioImagen.fechaAlta) : null;
      });
    }
    return res;
  }
}
