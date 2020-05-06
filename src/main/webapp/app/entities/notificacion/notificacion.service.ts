import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INotificacion } from 'app/shared/model/notificacion.model';

type EntityResponseType = HttpResponse<INotificacion>;
type EntityArrayResponseType = HttpResponse<INotificacion[]>;

@Injectable()
export class NotificacionService {
  public resourceUrl = SERVER_API_URL + 'api/notificacions';

  constructor(protected http: HttpClient) {}

  create(notificacion: INotificacion): Observable<EntityResponseType> {
    return this.http.post<INotificacion>(this.resourceUrl, notificacion, { observe: 'response' });
  }

  update(notificacion: INotificacion): Observable<EntityResponseType> {
    return this.http.put<INotificacion>(this.resourceUrl, notificacion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INotificacion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INotificacion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
