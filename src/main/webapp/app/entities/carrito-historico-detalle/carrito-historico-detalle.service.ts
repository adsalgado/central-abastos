import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICarritoHistoricoDetalle } from 'app/shared/model/carrito-historico-detalle.model';

type EntityResponseType = HttpResponse<ICarritoHistoricoDetalle>;
type EntityArrayResponseType = HttpResponse<ICarritoHistoricoDetalle[]>;

@Injectable()
export class CarritoHistoricoDetalleService {
  public resourceUrl = SERVER_API_URL + 'api/carrito-historico-detalles';

  constructor(protected http: HttpClient) {}

  create(carritoHistoricoDetalle: ICarritoHistoricoDetalle): Observable<EntityResponseType> {
    return this.http.post<ICarritoHistoricoDetalle>(this.resourceUrl, carritoHistoricoDetalle, { observe: 'response' });
  }

  update(carritoHistoricoDetalle: ICarritoHistoricoDetalle): Observable<EntityResponseType> {
    return this.http.put<ICarritoHistoricoDetalle>(this.resourceUrl, carritoHistoricoDetalle, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICarritoHistoricoDetalle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICarritoHistoricoDetalle[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
