import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductoImagen } from 'app/shared/model/producto-imagen.model';

type EntityResponseType = HttpResponse<IProductoImagen>;
type EntityArrayResponseType = HttpResponse<IProductoImagen[]>;

@Injectable()
export class ProductoImagenService {
  public resourceUrl = SERVER_API_URL + 'api/producto-imagens';

  constructor(protected http: HttpClient) {}

  create(productoImagen: IProductoImagen): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productoImagen);
    return this.http
      .post<IProductoImagen>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(productoImagen: IProductoImagen): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productoImagen);
    return this.http
      .put<IProductoImagen>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProductoImagen>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProductoImagen[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  findByProductpProveedorId(productoProveedorId?: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<IProductoImagen[]>(`${this.resourceUrl}/producto-proveedor/${productoProveedorId}`, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(productoImagen: IProductoImagen): IProductoImagen {
    const copy: IProductoImagen = Object.assign({}, productoImagen, {});
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((productoImagen: IProductoImagen) => {});
    }
    return res;
  }
}
