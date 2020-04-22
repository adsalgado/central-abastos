import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdjunto } from 'app/shared/model/adjunto.model';

type EntityResponseType = HttpResponse<IAdjunto>;
type EntityArrayResponseType = HttpResponse<IAdjunto[]>;

@Injectable({ providedIn: 'root' })
export class AdjuntoService {
  public resourceUrl = SERVER_API_URL + 'api/adjuntos';

  constructor(protected http: HttpClient) {}

  create(adjunto: IAdjunto): Observable<EntityResponseType> {
    return this.http.post<IAdjunto>(this.resourceUrl, adjunto, { observe: 'response' });
  }

  update(adjunto: IAdjunto): Observable<EntityResponseType> {
    return this.http.put<IAdjunto>(this.resourceUrl, adjunto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdjunto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdjunto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
