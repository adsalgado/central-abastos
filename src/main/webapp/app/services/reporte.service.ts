import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { IReporteCostosRequest } from 'app/shared/model/reporte-costos-request.model';

type EntityResponseType = HttpResponse<IProveedor>;
type EntityArrayResponseType = HttpResponse<IProveedor[]>;

@Injectable()
export class ReporteService {
  public resourceUrl = SERVER_API_URL + 'api/reportes';
  public pathReporteCostos = SERVER_API_URL + '/reporte-costos';

  constructor(protected http: HttpClient) {}

  getReporteCostos(req?: IReporteCostosRequest): Observable<EntityArrayResponseType> {
    return this.http.post<any[]>(this.resourceUrl, req, { observe: 'response' });
  }
}
