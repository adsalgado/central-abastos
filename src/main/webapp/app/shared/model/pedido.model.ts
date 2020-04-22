import { Moment } from 'moment';
import { IPedidoDetalle } from 'app/shared/model/pedido-detalle.model';
import { IHistoricoPedido } from 'app/shared/model/historico-pedido.model';

export interface IPedido {
  id?: number;
  totalSinIva?: number;
  comisionTransportista?: number;
  comisionPreparador?: number;
  total?: number;
  fechaPedido?: Moment;
  fechaPreparacion?: Moment;
  fechaCobro?: Moment;
  fechaEntrega?: Moment;
  pedidoDetalles?: IPedidoDetalle[];
  historicoPedidos?: IHistoricoPedido[];
  clienteId?: number;
  estatusId?: number;
  transportistaId?: number;
  recolectorId?: number;
}

export class Pedido implements IPedido {
  constructor(
    public id?: number,
    public totalSinIva?: number,
    public comisionTransportista?: number,
    public comisionPreparador?: number,
    public total?: number,
    public fechaPedido?: Moment,
    public fechaPreparacion?: Moment,
    public fechaCobro?: Moment,
    public fechaEntrega?: Moment,
    public pedidoDetalles?: IPedidoDetalle[],
    public historicoPedidos?: IHistoricoPedido[],
    public clienteId?: number,
    public estatusId?: number,
    public transportistaId?: number,
    public recolectorId?: number
  ) {}
}
