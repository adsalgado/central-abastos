import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITransportistaTarifa } from 'app/shared/model/transportista-tarifa.model';

type EntityResponseType = HttpResponse<ITransportistaTarifa>;
type EntityArrayResponseType = HttpResponse<ITransportistaTarifa[]>;

@Injectable({ providedIn: 'root' })
export class TransportistaTarifaService {
  public resourceUrl = SERVER_API_URL + 'api/transportista-tarifas';

  constructor(protected http: HttpClient) {}

  create(transportistaTarifa: ITransportistaTarifa): Observable<EntityResponseType> {
    return this.http.post<ITransportistaTarifa>(this.resourceUrl, transportistaTarifa, { observe: 'response' });
  }

  update(transportistaTarifa: ITransportistaTarifa): Observable<EntityResponseType> {
    return this.http.put<ITransportistaTarifa>(this.resourceUrl, transportistaTarifa, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITransportistaTarifa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransportistaTarifa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
