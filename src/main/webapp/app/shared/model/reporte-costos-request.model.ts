import { Moment } from 'moment';

export interface IReporteCostosRequest {
  proveedorId?: number;
  fechaInicial?: Date;
  fechaFinal?: Date;
}

export class ReporteCostosRequest implements IReporteCostosRequest {
  constructor(public proveedorId?: number, public fechaInicial?: Date, public fechaFinal?: Date) {}
}
