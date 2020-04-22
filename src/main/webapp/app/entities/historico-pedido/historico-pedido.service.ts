import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHistoricoPedido } from 'app/shared/model/historico-pedido.model';

type EntityResponseType = HttpResponse<IHistoricoPedido>;
type EntityArrayResponseType = HttpResponse<IHistoricoPedido[]>;

@Injectable({ providedIn: 'root' })
export class HistoricoPedidoService {
  public resourceUrl = SERVER_API_URL + 'api/historico-pedidos';

  constructor(protected http: HttpClient) {}

  create(historicoPedido: IHistoricoPedido): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historicoPedido);
    return this.http
      .post<IHistoricoPedido>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(historicoPedido: IHistoricoPedido): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historicoPedido);
    return this.http
      .put<IHistoricoPedido>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHistoricoPedido>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHistoricoPedido[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(historicoPedido: IHistoricoPedido): IHistoricoPedido {
    const copy: IHistoricoPedido = Object.assign({}, historicoPedido, {
      fechaEstatus:
        historicoPedido.fechaEstatus != null && historicoPedido.fechaEstatus.isValid()
          ? historicoPedido.fechaEstatus.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaEstatus = res.body.fechaEstatus != null ? moment(res.body.fechaEstatus) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((historicoPedido: IHistoricoPedido) => {
        historicoPedido.fechaEstatus = historicoPedido.fechaEstatus != null ? moment(historicoPedido.fechaEstatus) : null;
      });
    }
    return res;
  }
}
