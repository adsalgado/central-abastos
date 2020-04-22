import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IParametrosAplicacion } from 'app/shared/model/parametros-aplicacion.model';

type EntityResponseType = HttpResponse<IParametrosAplicacion>;
type EntityArrayResponseType = HttpResponse<IParametrosAplicacion[]>;

@Injectable({ providedIn: 'root' })
export class ParametrosAplicacionService {
  public resourceUrl = SERVER_API_URL + 'api/parametros-aplicacions';

  constructor(protected http: HttpClient) {}

  create(parametrosAplicacion: IParametrosAplicacion): Observable<EntityResponseType> {
    return this.http.post<IParametrosAplicacion>(this.resourceUrl, parametrosAplicacion, { observe: 'response' });
  }

  update(parametrosAplicacion: IParametrosAplicacion): Observable<EntityResponseType> {
    return this.http.put<IParametrosAplicacion>(this.resourceUrl, parametrosAplicacion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IParametrosAplicacion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IParametrosAplicacion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
