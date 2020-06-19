import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQueja, Queja } from 'app/shared/model/queja.model';
import { TrackingQueja } from 'app/models/tracking-queja';

type EntityResponseType = HttpResponse<IQueja>;
type EntityArrayResponseType = HttpResponse<IQueja[]>;

@Injectable()
export class QuejaService {
  public resourceUrl = SERVER_API_URL + 'api/quejas';
  public resourceTrackingQUejasURL = SERVER_API_URL + 'api/tracking-quejas';

  constructor(protected http: HttpClient) {}

  // create(queja: IQueja): Observable<EntityResponseType> {
  //   return this.http.post<IQueja>(this.resourceUrl, queja, { observe: 'response' });
  // }

  // update(queja: IQueja): Observable<EntityResponseType> {
  //   return this.http.put<IQueja>(this.resourceUrl, queja, { observe: 'response' });
  // }

  // find(id: number): Observable<EntityResponseType> {
  //   return this.http.get<IQueja>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  // }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQueja[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  addTracking(tracking: TrackingQueja): Observable<TrackingQueja> {
    return this.http.post<TrackingQueja>(this.resourceTrackingQUejasURL, tracking);
  }

  // delete(id: number): Observable<HttpResponse<any>> {
  //   return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  // }

  // getQuejas() : Observable<Queja[]> {
  //     return this.http.get<Queja[]>(this.resourceUrl);
  // }
}
