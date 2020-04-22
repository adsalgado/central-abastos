import { Moment } from 'moment';
import { ICarritoHistoricoDetalle } from 'app/shared/model/carrito-historico-detalle.model';

export interface ICarritoHistorico {
  id?: number;
  nombre?: string;
  fechaAlta?: Moment;
  carritoHistoricoDetalles?: ICarritoHistoricoDetalle[];
  clienteId?: number;
}

export class CarritoHistorico implements ICarritoHistorico {
  constructor(
    public id?: number,
    public nombre?: string,
    public fechaAlta?: Moment,
    public carritoHistoricoDetalles?: ICarritoHistoricoDetalle[],
    public clienteId?: number
  ) {}
}
