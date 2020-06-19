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
    public id?: Number,
    public tipoUsuarioId?: Number,
    public tipoUsuario?: TipoUsuario,
    public usuarioId?: Number,
    public usuario?: User,
    public pedidoProveedor?: any,
    public pedidoProveedorId?: Number,
    public estatusId?: Number,
    public estatus?: Estatus,
    public fechaAlta?: Date,
    public tracking?: TrackingQueja[]
  ) {}
}
