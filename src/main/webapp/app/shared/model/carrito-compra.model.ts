export interface ICarritoCompra {
  id?: number;
  cantidad?: number;
  precio?: number;
  clienteId?: number;
  productoId?: number;
}

export class CarritoCompra implements ICarritoCompra {
  constructor(
    public id?: number,
    public cantidad?: number,
    public precio?: number,
    public clienteId?: number,
    public productoId?: number
  ) {}
}
