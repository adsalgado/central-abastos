import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductoProveedor } from 'app/shared/model/producto-proveedor.model';
import { IAdjunto } from 'app/shared/model/adjunto.model';
import { IAbastosResponse } from 'app/shared/model/abastos-response.model';

type EntityResponseType = HttpResponse<IProductoProveedor>;
type EntityArrayResponseType = HttpResponse<IProductoProveedor[]>;

@Injectable({ providedIn: 'root' })
export class ProductoProveedorService {
  public resourceUrl = SERVER_API_URL + 'api/proveedor/proveedor-productos';

  constructor(protected http: HttpClient) {}

  create(productoProveedor: IProductoProveedor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productoProveedor);
    return this.http
      .post<IProductoProveedor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(productoProveedor: IProductoProveedor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productoProveedor);
    return this.http
      .put<IProductoProveedor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProductoProveedor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProductoProveedor[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  cargaMasiva(adjunto: IAdjunto): Observable<HttpResponse<IAbastosResponse>> {
    return this.http.post<IAbastosResponse>(`${this.resourceUrl}/carga-masiva`, adjunto, { observe: 'response' });
  }

  protected convertDateFromClient(productoProveedor: IProductoProveedor): IProductoProveedor {
    const copy: IProductoProveedor = Object.assign({}, productoProveedor, {
      fechaAlta: productoProveedor.fechaAlta != null && productoProveedor.fechaAlta.isValid() ? productoProveedor.fechaAlta.toJSON() : null,
      fechaModificacion:
        productoProveedor.fechaModificacion != null && productoProveedor.fechaModificacion.isValid()
          ? productoProveedor.fechaModificacion.toJSON()
          : null
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
      res.body.forEach((productoProveedor: IProductoProveedor) => {
        productoProveedor.fechaAlta = productoProveedor.fechaAlta != null ? moment(productoProveedor.fechaAlta) : null;
        productoProveedor.fechaModificacion =
          productoProveedor.fechaModificacion != null ? moment(productoProveedor.fechaModificacion) : null;
      });
    }
    return res;
  }
}
