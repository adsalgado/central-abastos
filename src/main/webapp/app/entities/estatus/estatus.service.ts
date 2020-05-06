import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEstatus } from 'app/shared/model/estatus.model';

type EntityResponseType = HttpResponse<IEstatus>;
type EntityArrayResponseType = HttpResponse<IEstatus[]>;

@Injectable()
export class EstatusService {
  public resourceUrl = SERVER_API_URL + 'api/estatuses';

  constructor(protected http: HttpClient) {}

  create(estatus: IEstatus): Observable<EntityResponseType> {
    return this.http.post<IEstatus>(this.resourceUrl, estatus, { observe: 'response' });
  }

  update(estatus: IEstatus): Observable<EntityResponseType> {
    return this.http.put<IEstatus>(this.resourceUrl, estatus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
