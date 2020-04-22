import { Moment } from 'moment';

export interface IUsuarioImagen {
  id?: number;
  fechaAlta?: Moment;
  usuarioId?: number;
  adjuntoId?: number;
}

export class UsuarioImagen implements IUsuarioImagen {
  constructor(public id?: number, public fechaAlta?: Moment, public usuarioId?: number, public adjuntoId?: number) {}
}
