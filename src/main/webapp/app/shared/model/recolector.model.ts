import { Moment } from 'moment';
import { IPedido } from 'app/shared/model/pedido.model';
import { IRecolectorTarifa } from 'app/shared/model/recolector-tarifa.model';

export interface IRecolector {
  id?: number;
  nombre?: string;
  fechaAlta?: Moment;
  fechaModificacion?: Moment;
  usuarioAltaId?: number;
  usuarioModificacionId?: number;
  pedidos?: IPedido[];
  recolectorTarifas?: IRecolectorTarifa[];
  empresaId?: number;
}

export class Recolector implements IRecolector {
  constructor(
    public id?: number,
    public nombre?: string,
    public fechaAlta?: Moment,
    public fechaModificacion?: Moment,
    public usuarioAltaId?: number,
    public usuarioModificacionId?: number,
    public pedidos?: IPedido[],
    public recolectorTarifas?: IRecolectorTarifa[],
    public empresaId?: number
  ) {}
}
