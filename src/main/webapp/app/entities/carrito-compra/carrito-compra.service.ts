import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICarritoCompra } from 'app/shared/model/carrito-compra.model';

type EntityResponseType = HttpResponse<ICarritoCompra>;
type EntityArrayResponseType = HttpResponse<ICarritoCompra[]>;

@Injectable({ providedIn: 'root' })
export class CarritoCompraService {
  public resourceUrl = SERVER_API_URL + 'api/carrito-compras';

  constructor(protected http: HttpClient) {}

  create(carritoCompra: ICarritoCompra): Observable<EntityResponseType> {
    return this.http.post<ICarritoCompra>(this.resourceUrl, carritoCompra, { observe: 'response' });
  }

  update(carritoCompra: ICarritoCompra): Observable<EntityResponseType> {
    return this.http.put<ICarritoCompra>(this.resourceUrl, carritoCompra, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICarritoCompra>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICarritoCompra[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
