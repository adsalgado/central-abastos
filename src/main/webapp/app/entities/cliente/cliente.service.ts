import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICliente } from 'app/shared/model/cliente.model';

type EntityResponseType = HttpResponse<ICliente>;
type EntityArrayResponseType = HttpResponse<ICliente[]>;

@Injectable()
export class ClienteService {
  public resourceUrl = SERVER_API_URL + 'api/clientes';

  constructor(protected http: HttpClient) {}

  create(cliente: ICliente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cliente);
    return this.http
      .post<ICliente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cliente: ICliente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cliente);
    return this.http
      .put<ICliente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICliente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICliente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(cliente: ICliente): ICliente {
    const copy: ICliente = Object.assign({}, cliente, {
      fechaAlta: cliente.fechaAlta != null && cliente.fechaAlta.isValid() ? cliente.fechaAlta.toJSON() : null,
      fechaModificacion:
        cliente.fechaModificacion != null && cliente.fechaModificacion.isValid() ? cliente.fechaModificacion.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaAlta = res.body.fechaAlta != null ? moment(res.body.fechaAlta) : null;
      res.body.fechaModificacion = res.body.fechaModificacion != null ? moment(res.body.fechaModificacion) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((cliente: ICliente) => {
        cliente.fechaAlta = cliente.fechaAlta != null ? moment(cliente.fechaAlta) : null;
        cliente.fechaModificacion = cliente.fechaModificacion != null ? moment(cliente.fechaModificacion) : null;
      });
    }
    return res;
  }
}
