import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoOferta } from 'app/shared/model/tipo-oferta.model';

type EntityResponseType = HttpResponse<ITipoOferta>;
type EntityArrayResponseType = HttpResponse<ITipoOferta[]>;

@Injectable()
export class TipoOfertaService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-ofertas';

  constructor(protected http: HttpClient) {}

  create(tipoOferta: ITipoOferta): Observable<EntityResponseType> {
    return this.http.post<ITipoOferta>(this.resourceUrl, tipoOferta, { observe: 'response' });
  }

  update(tipoOferta: ITipoOferta): Observable<EntityResponseType> {
    return this.http.put<ITipoOferta>(this.resourceUrl, tipoOferta, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoOferta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoOferta[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
