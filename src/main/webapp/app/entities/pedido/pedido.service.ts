import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPedido } from 'app/shared/model/pedido.model';

type EntityResponseType = HttpResponse<IPedido>;
type EntityArrayResponseType = HttpResponse<IPedido[]>;

@Injectable({ providedIn: 'root' })
export class PedidoService {
  public resourceUrl = SERVER_API_URL + 'api/pedidos';

  constructor(protected http: HttpClient) {}

  create(pedido: IPedido): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pedido);
    return this.http
      .post<IPedido>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pedido: IPedido): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pedido);
    return this.http
      .put<IPedido>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPedido>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPedido[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pedido: IPedido): IPedido {
    const copy: IPedido = Object.assign({}, pedido, {
      fechaPedido: pedido.fechaPedido != null && pedido.fechaPedido.isValid() ? pedido.fechaPedido.format(DATE_FORMAT) : null,
      fechaPreparacion:
        pedido.fechaPreparacion != null && pedido.fechaPreparacion.isValid() ? pedido.fechaPreparacion.format(DATE_FORMAT) : null,
      fechaCobro: pedido.fechaCobro != null && pedido.fechaCobro.isValid() ? pedido.fechaCobro.format(DATE_FORMAT) : null,
      fechaEntrega: pedido.fechaEntrega != null && pedido.fechaEntrega.isValid() ? pedido.fechaEntrega.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaPedido = res.body.fechaPedido != null ? moment(res.body.fechaPedido) : null;
      res.body.fechaPreparacion = res.body.fechaPreparacion != null ? moment(res.body.fechaPreparacion) : null;
      res.body.fechaCobro = res.body.fechaCobro != null ? moment(res.body.fechaCobro) : null;
      res.body.fechaEntrega = res.body.fechaEntrega != null ? moment(res.body.fechaEntrega) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pedido: IPedido) => {
        pedido.fechaPedido = pedido.fechaPedido != null ? moment(pedido.fechaPedido) : null;
        pedido.fechaPreparacion = pedido.fechaPreparacion != null ? moment(pedido.fechaPreparacion) : null;
        pedido.fechaCobro = pedido.fechaCobro != null ? moment(pedido.fechaCobro) : null;
        pedido.fechaEntrega = pedido.fechaEntrega != null ? moment(pedido.fechaEntrega) : null;
      });
    }
    return res;
  }
}
