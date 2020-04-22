import { Moment } from 'moment';
import { IPedido } from 'app/shared/model/pedido.model';
import { ITransportistaTarifa } from 'app/shared/model/transportista-tarifa.model';

export interface ITransportista {
  id?: number;
  nombre?: string;
  fechaAlta?: Moment;
  fechaModificacion?: Moment;
  usuarioAltaId?: number;
  usuarioModificacionId?: number;
  pedidos?: IPedido[];
  transportistaTarifas?: ITransportistaTarifa[];
  empresaId?: number;
}

export class Transportista implements ITransportista {
  constructor(
    public id?: number,
    public nombre?: string,
    public fechaAlta?: Moment,
    public fechaModificacion?: Moment,
    public usuarioAltaId?: number,
    public usuarioModificacionId?: number,
    public pedidos?: IPedido[],
    public transportistaTarifas?: ITransportistaTarifa[],
    public empresaId?: number
  ) {}
}
