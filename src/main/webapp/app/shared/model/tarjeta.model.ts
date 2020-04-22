import { Moment } from 'moment';

export interface ITarjeta {
  id?: number;
  numeroTarjeta?: string;
  fechaCaducidad?: string;
  numeroSeguridad?: string;
  fechaAlta?: Moment;
  clienteId?: number;
}

export class Tarjeta implements ITarjeta {
  constructor(
    public id?: number,
    public numeroTarjeta?: string,
    public fechaCaducidad?: string,
    public numeroSeguridad?: string,
    public fechaAlta?: Moment,
    public clienteId?: number
  ) {}
}
