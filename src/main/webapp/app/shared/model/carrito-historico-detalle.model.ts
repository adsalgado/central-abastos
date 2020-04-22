export interface ICarritoHistoricoDetalle {
  id?: number;
  cantidad?: number;
  precio?: number;
  productoId?: number;
  carritoHistoricoId?: number;
}

export class CarritoHistoricoDetalle implements ICarritoHistoricoDetalle {
  constructor(
    public id?: number,
    public cantidad?: number,
    public precio?: number,
    public productoId?: number,
    public carritoHistoricoId?: number
  ) {}
}
