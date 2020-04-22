import { Moment } from 'moment';

export interface IChat {
  id?: number;
  mensaje?: string;
  fecha?: Moment;
  usuarioFuenteId?: number;
  usuarioDestinoId?: number;
  adjuntoId?: number;
}

export class Chat implements IChat {
  constructor(
    public id?: number,
    public mensaje?: string,
    public fecha?: Moment,
    public usuarioFuenteId?: number,
    public usuarioDestinoId?: number,
    public adjuntoId?: number
  ) {}
}
