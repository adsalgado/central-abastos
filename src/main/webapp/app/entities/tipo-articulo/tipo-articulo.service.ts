import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoArticulo } from 'app/shared/model/tipo-articulo.model';

type EntityResponseType = HttpResponse<ITipoArticulo>;
type EntityArrayResponseType = HttpResponse<ITipoArticulo[]>;

@Injectable()
export class TipoArticuloService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-articulos';

  constructor(protected http: HttpClient) {}

  create(tipoArticulo: ITipoArticulo): Observable<EntityResponseType> {
    return this.http.post<ITipoArticulo>(this.resourceUrl, tipoArticulo, { observe: 'response' });
  }

  update(tipoArticulo: ITipoArticulo): Observable<EntityResponseType> {
    return this.http.put<ITipoArticulo>(this.resourceUrl, tipoArticulo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoArticulo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoArticulo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
