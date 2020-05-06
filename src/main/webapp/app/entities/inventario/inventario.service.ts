import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInventario } from 'app/shared/model/inventario.model';

type EntityResponseType = HttpResponse<IInventario>;
type EntityArrayResponseType = HttpResponse<IInventario[]>;

@Injectable()
export class InventarioService {
  public resourceUrl = SERVER_API_URL + 'api/inventarios';

  constructor(protected http: HttpClient) {}

  create(inventario: IInventario): Observable<EntityResponseType> {
    return this.http.post<IInventario>(this.resourceUrl, inventario, { observe: 'response' });
  }

  update(inventario: IInventario): Observable<EntityResponseType> {
    return this.http.put<IInventario>(this.resourceUrl, inventario, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInventario>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInventario[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
