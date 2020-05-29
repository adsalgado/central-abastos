import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPromocion } from 'app/shared/model/promocion.model';

type EntityResponseType = HttpResponse<IPromocion>;
type EntityArrayResponseType = HttpResponse<IPromocion[]>;

@Injectable()
export class PromocionService {
  public resourceUrl = SERVER_API_URL + 'api/promociones';

  constructor(protected http: HttpClient) {}

  create(promocion: IPromocion): Observable<EntityResponseType> {
    return this.http.post<IPromocion>(this.resourceUrl, promocion, { observe: 'response' });
  }

  update(promocion: IPromocion): Observable<EntityResponseType> {
    return this.http.put<IPromocion>(this.resourceUrl, promocion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPromocion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPromocion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
