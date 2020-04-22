export interface IPedidoDetalle {
  id?: number;
  cantidad?: number;
  totalSinIva?: number;
  total?: number;
  pedidoId?: number;
  productoId?: number;
  estatusId?: number;
}

export class PedidoDetalle implements IPedidoDetalle {
  constructor(
    public id?: number,
    public cantidad?: number,
    public totalSinIva?: number,
    public total?: number,
    public pedidoId?: number,
    public productoId?: number,
    public estatusId?: number
  ) {}
}
