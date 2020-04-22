import { Moment } from 'moment';

export interface IOfertaProveedor {
  id?: number;
  fechaInicio?: Moment;
  fechaFin?: Moment;
  proveedorId?: number;
  productoId?: number;
  estatusId?: number;
  tipoOfertaId?: number;
}

export class OfertaProveedor implements IOfertaProveedor {
  constructor(
    public id?: number,
    public fechaInicio?: Moment,
    public fechaFin?: Moment,
    public proveedorId?: number,
    public productoId?: number,
    public estatusId?: number,
    public tipoOfertaId?: number
  ) {}
}
