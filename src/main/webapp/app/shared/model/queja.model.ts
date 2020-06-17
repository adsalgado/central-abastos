import { TrackingQueja } from 'app/models/tracking-queja';
import { User } from 'app/core/user/user.model';
import { Estatus } from './estatus.model';

export interface IQueja {
  id?: Number;
  tipoUsuario?: String;
  createdAt?: Date;
  //tracking?: TrackingQueja[];
  status?: Estatus;
  requestedBy?: User;
  folioPedido?: String;
}

export class Queja implements IQueja {
  constructor(
    public id?: number,
    public tipoUsuario?: String,
    public createdAt?: Date,
    // public tracking?: TrackingQueja[],
    public status?: Estatus,
    public requestedBy?: User,
    public folioPedido?: String
  ) {}
}
