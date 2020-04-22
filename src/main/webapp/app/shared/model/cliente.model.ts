import { Moment } from 'moment';
import { ITarjeta } from 'app/shared/model/tarjeta.model';
import { IDireccion } from 'app/shared/model/direccion.model';
import { ICarritoCompra } from 'app/shared/model/carrito-compra.model';
import { ICarritoHistorico } from 'app/shared/model/carrito-historico.model';
import { IPedido } from 'app/shared/model/pedido.model';

export interface ICliente {
  id?: number;
  nombre?: string;
  apellidoPaterno?: string;
  email?: string;
  telefono?: string;
  fechaAlta?: Moment;
  fechaModificacion?: Moment;
  usuarioAltaId?: number;
  usuarioModificacionId?: number;
  tarjetas?: ITarjeta[];
  direccions?: IDireccion[];
  carritoCompras?: ICarritoCompra[];
  carritoHistoricos?: ICarritoHistorico[];
  pedidos?: IPedido[];
  estatusId?: number;
  empresaId?: number;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public nombre?: string,
    public apellidoPaterno?: string,
    public email?: string,
    public telefono?: string,
    public fechaAlta?: Moment,
    public fechaModificacion?: Moment,
    public usuarioAltaId?: number,
    public usuarioModificacionId?: number,
    public tarjetas?: ITarjeta[],
    public direccions?: IDireccion[],
    public carritoCompras?: ICarritoCompra[],
    public carritoHistoricos?: ICarritoHistorico[],
    public pedidos?: IPedido[],
    public estatusId?: number,
    public empresaId?: number
  ) {}
}
