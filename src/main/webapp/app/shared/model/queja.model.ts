import { TrackingQueja } from 'app/models/tracking-queja';
import { User, TipoUsuario } from 'app/core/user/user.model';
import { Estatus } from './estatus.model';

export interface IQueja {
  id?: number;
  tipoUsuarioId?: number;
  tipoUsuario?: TipoUsuario;
  usuarioId?: number;
  usuario?: User;
  pedidoProveedor?: any;
  pedidoProveedorId?: number;
  estatusId?: number;
  estatus?: Estatus;
  fechaAlta?: Date;
  tracking?: TrackingQueja[];
  //requestedBy?: User;
  // folioPedido?: String;
}

export class Queja implements IQueja {
  constructor(
    public id?: number,
    public tipoUsuarioId?: number,
    public tipoUsuario?: TipoUsuario,
    public usuarioId?: number,
    public usuario?: User,
    public pedidoProveedor?: any,
    public pedidoProveedorId?: number,
    public estatusId?: number,
    public estatus?: Estatus,
    public fechaAlta?: Date,
    public tracking?: TrackingQueja[]
  ) {}
}
