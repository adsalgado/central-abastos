import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICarritoHistorico } from 'app/shared/model/carrito-historico.model';

type EntityResponseType = HttpResponse<ICarritoHistorico>;
type EntityArrayResponseType = HttpResponse<ICarritoHistorico[]>;

@Injectable()
export class CarritoHistoricoService {
  public resourceUrl = SERVER_API_URL + 'api/carrito-historicos';

  constructor(protected http: HttpClient) {}

  create(carritoHistorico: ICarritoHistorico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carritoHistorico);
    return this.http
      .post<ICarritoHistorico>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(carritoHistorico: ICarritoHistorico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carritoHistorico);
    return this.http
      .put<ICarritoHistorico>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICarritoHistorico>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICarritoHistorico[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(carritoHistorico: ICarritoHistorico): ICarritoHistorico {
    const copy: ICarritoHistorico = Object.assign({}, carritoHistorico, {
      fechaAlta:
        carritoHistorico.fechaAlta != null && carritoHistorico.fechaAlta.isValid() ? carritoHistorico.fechaAlta.format(DATE_FORMAT) : null
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
      res.body.forEach((carritoHistorico: ICarritoHistorico) => {
        carritoHistorico.fechaAlta = carritoHistorico.fechaAlta != null ? moment(carritoHistorico.fechaAlta) : null;
      });
    }
    return res;
  }
}
