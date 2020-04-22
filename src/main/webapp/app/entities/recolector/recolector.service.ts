import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRecolector } from 'app/shared/model/recolector.model';

type EntityResponseType = HttpResponse<IRecolector>;
type EntityArrayResponseType = HttpResponse<IRecolector[]>;

@Injectable({ providedIn: 'root' })
export class RecolectorService {
  public resourceUrl = SERVER_API_URL + 'api/recolectors';

  constructor(protected http: HttpClient) {}

  create(recolector: IRecolector): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recolector);
    return this.http
      .post<IRecolector>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(recolector: IRecolector): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recolector);
    return this.http
      .put<IRecolector>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRecolector>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRecolector[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(recolector: IRecolector): IRecolector {
    const copy: IRecolector = Object.assign({}, recolector, {
      fechaAlta: recolector.fechaAlta != null && recolector.fechaAlta.isValid() ? recolector.fechaAlta.toJSON() : null,
      fechaModificacion:
        recolector.fechaModificacion != null && recolector.fechaModificacion.isValid() ? recolector.fechaModificacion.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaAlta = res.body.fechaAlta != null ? moment(res.body.fechaAlta) : null;
      res.body.fechaModificacion = res.body.fechaModificacion != null ? moment(res.body.fechaModificacion) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((recolector: IRecolector) => {
        recolector.fechaAlta = recolector.fechaAlta != null ? moment(recolector.fechaAlta) : null;
        recolector.fechaModificacion = recolector.fechaModificacion != null ? moment(recolector.fechaModificacion) : null;
      });
    }
    return res;
  }
}
