import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPedidoDetalle } from 'app/shared/model/pedido-detalle.model';

type EntityResponseType = HttpResponse<IPedidoDetalle>;
type EntityArrayResponseType = HttpResponse<IPedidoDetalle[]>;

@Injectable({ providedIn: 'root' })
export class PedidoDetalleService {
  public resourceUrl = SERVER_API_URL + 'api/pedido-detalles';

  constructor(protected http: HttpClient) {}

  create(pedidoDetalle: IPedidoDetalle): Observable<EntityResponseType> {
    return this.http.post<IPedidoDetalle>(this.resourceUrl, pedidoDetalle, { observe: 'response' });
  }

  update(pedidoDetalle: IPedidoDetalle): Observable<EntityResponseType> {
    return this.http.put<IPedidoDetalle>(this.resourceUrl, pedidoDetalle, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPedidoDetalle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPedidoDetalle[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
