import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IChatPrivate } from 'app/shared/model/chat-private.model';

type EntityResponseType = HttpResponse<IChatPrivate>;
type EntityArrayResponseType = HttpResponse<IChatPrivate[]>;

@Injectable()
export class ChatPrivateService {
  public resourceUrl = SERVER_API_URL + 'api/chat-privates';

  constructor(protected http: HttpClient) {}

  create(chatPrivate: IChatPrivate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chatPrivate);
    return this.http
      .post<IChatPrivate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(chatPrivate: IChatPrivate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chatPrivate);
    return this.http
      .put<IChatPrivate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IChatPrivate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IChatPrivate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(chatPrivate: IChatPrivate): IChatPrivate {
    const copy: IChatPrivate = Object.assign({}, chatPrivate, {
      fecha: chatPrivate.fecha != null && chatPrivate.fecha.isValid() ? chatPrivate.fecha.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((chatPrivate: IChatPrivate) => {
        chatPrivate.fecha = chatPrivate.fecha != null ? moment(chatPrivate.fecha) : null;
      });
    }
    return res;
  }
}
