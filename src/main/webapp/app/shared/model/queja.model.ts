import { TrackingQueja } from 'app/models/tracking-queja';
import { User, TipoUsuario } from 'app/core/user/user.model';
import { Estatus } from './estatus.model';

export interface IQueja {
  id?: Number;
  tipoUsuarioId?: Number;
  tipoUsuario?: TipoUsuario;
  usuarioId?: Number;
  usuario?: User;
  pedidoProveedor?: any;
  pedidoProveedorId?: Number;
  estatusId?: Number;
  estatus?: Estatus;
  fechaAlta?: Date;
  tracking?: TrackingQueja[];
  //requestedBy?: User;
  // folioPedido?: String;
}

export class Queja implements IQueja {
  constructor(
    id?: Number,
    tipoUsuarioId?: Number,
    tipoUsuario?: TipoUsuario,
    usuarioId?: Number,
    usuario?: User,
    pedidoProveedor?: any,
    pedidoProveedorId?: Number,
    estatusId?: Number,
    estatus?: Estatus,
    fechaAlta?: Date,
    tracking?: TrackingQueja[]
  ) {}
}
