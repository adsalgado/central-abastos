import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOfertaProveedor } from 'app/shared/model/oferta-proveedor.model';

type EntityResponseType = HttpResponse<IOfertaProveedor>;
type EntityArrayResponseType = HttpResponse<IOfertaProveedor[]>;

@Injectable()
export class OfertaProveedorService {
  public resourceUrl = SERVER_API_URL + 'api/oferta-proveedors';

  constructor(protected http: HttpClient) {}

  create(ofertaProveedor: IOfertaProveedor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ofertaProveedor);
    return this.http
      .post<IOfertaProveedor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ofertaProveedor: IOfertaProveedor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ofertaProveedor);
    return this.http
      .put<IOfertaProveedor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOfertaProveedor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOfertaProveedor[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(ofertaProveedor: IOfertaProveedor): IOfertaProveedor {
    const copy: IOfertaProveedor = Object.assign({}, ofertaProveedor, {
      fechaInicio:
        ofertaProveedor.fechaInicio != null && ofertaProveedor.fechaInicio.isValid()
          ? ofertaProveedor.fechaInicio.format(DATE_FORMAT)
          : null,
      fechaFin: ofertaProveedor.fechaFin != null && ofertaProveedor.fechaFin.isValid() ? ofertaProveedor.fechaFin.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaInicio = res.body.fechaInicio != null ? moment(res.body.fechaInicio) : null;
      res.body.fechaFin = res.body.fechaFin != null ? moment(res.body.fechaFin) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((ofertaProveedor: IOfertaProveedor) => {
        ofertaProveedor.fechaInicio = ofertaProveedor.fechaInicio != null ? moment(ofertaProveedor.fechaInicio) : null;
        ofertaProveedor.fechaFin = ofertaProveedor.fechaFin != null ? moment(ofertaProveedor.fechaFin) : null;
      });
    }
    return res;
  }
}
