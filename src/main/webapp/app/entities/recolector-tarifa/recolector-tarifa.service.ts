import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRecolectorTarifa } from 'app/shared/model/recolector-tarifa.model';

type EntityResponseType = HttpResponse<IRecolectorTarifa>;
type EntityArrayResponseType = HttpResponse<IRecolectorTarifa[]>;

@Injectable({ providedIn: 'root' })
export class RecolectorTarifaService {
  public resourceUrl = SERVER_API_URL + 'api/recolector-tarifas';

  constructor(protected http: HttpClient) {}

  create(recolectorTarifa: IRecolectorTarifa): Observable<EntityResponseType> {
    return this.http.post<IRecolectorTarifa>(this.resourceUrl, recolectorTarifa, { observe: 'response' });
  }

  update(recolectorTarifa: IRecolectorTarifa): Observable<EntityResponseType> {
    return this.http.put<IRecolectorTarifa>(this.resourceUrl, recolectorTarifa, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRecolectorTarifa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRecolectorTarifa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
