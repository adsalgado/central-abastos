import { Moment } from 'moment';

export interface IHistoricoPedido {
  id?: number;
  fechaEstatus?: Moment;
  pedidoId?: number;
}

export class HistoricoPedido implements IHistoricoPedido {
  constructor(public id?: number, public fechaEstatus?: Moment, public pedidoId?: number) {}
}
