import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInventarioHistorico } from 'app/shared/model/inventario-historico.model';

type EntityResponseType = HttpResponse<IInventarioHistorico>;
type EntityArrayResponseType = HttpResponse<IInventarioHistorico[]>;

@Injectable({ providedIn: 'root' })
export class InventarioHistoricoService {
  public resourceUrl = SERVER_API_URL + 'api/inventario-historicos';

  constructor(protected http: HttpClient) {}

  create(inventarioHistorico: IInventarioHistorico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventarioHistorico);
    return this.http
      .post<IInventarioHistorico>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(inventarioHistorico: IInventarioHistorico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventarioHistorico);
    return this.http
      .put<IInventarioHistorico>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInventarioHistorico>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInventarioHistorico[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(inventarioHistorico: IInventarioHistorico): IInventarioHistorico {
    const copy: IInventarioHistorico = Object.assign({}, inventarioHistorico, {
      fechaMovimiento:
        inventarioHistorico.fechaMovimiento != null && inventarioHistorico.fechaMovimiento.isValid()
          ? inventarioHistorico.fechaMovimiento.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaMovimiento = res.body.fechaMovimiento != null ? moment(res.body.fechaMovimiento) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((inventarioHistorico: IInventarioHistorico) => {
        inventarioHistorico.fechaMovimiento =
          inventarioHistorico.fechaMovimiento != null ? moment(inventarioHistorico.fechaMovimiento) : null;
      });
    }
    return res;
  }
}
