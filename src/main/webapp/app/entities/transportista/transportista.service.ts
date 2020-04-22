import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITransportista } from 'app/shared/model/transportista.model';

type EntityResponseType = HttpResponse<ITransportista>;
type EntityArrayResponseType = HttpResponse<ITransportista[]>;

@Injectable({ providedIn: 'root' })
export class TransportistaService {
  public resourceUrl = SERVER_API_URL + 'api/transportistas';

  constructor(protected http: HttpClient) {}

  create(transportista: ITransportista): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transportista);
    return this.http
      .post<ITransportista>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transportista: ITransportista): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transportista);
    return this.http
      .put<ITransportista>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransportista>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransportista[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(transportista: ITransportista): ITransportista {
    const copy: ITransportista = Object.assign({}, transportista, {
      fechaAlta: transportista.fechaAlta != null && transportista.fechaAlta.isValid() ? transportista.fechaAlta.toJSON() : null,
      fechaModificacion:
        transportista.fechaModificacion != null && transportista.fechaModificacion.isValid()
          ? transportista.fechaModificacion.toJSON()
          : null
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
      res.body.forEach((transportista: ITransportista) => {
        transportista.fechaAlta = transportista.fechaAlta != null ? moment(transportista.fechaAlta) : null;
        transportista.fechaModificacion = transportista.fechaModificacion != null ? moment(transportista.fechaModificacion) : null;
      });
    }
    return res;
  }
}
