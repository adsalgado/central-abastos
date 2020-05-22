import { Moment } from 'moment';

export interface IProductoProveedor {
  id?: number;
  precioSinIva?: number;
  precio?: number;
  fechaAlta?: Moment;
  fechaModificacion?: Moment;
  proveedorId?: number;
  productoId?: number;
  estatusId?: number;
}

export class ProductoProveedor implements IProductoProveedor {
  constructor(
    public id?: number,
    public precioSinIva?: number,
    public precio?: number,
    public fechaAlta?: Moment,
    public fechaModificacion?: Moment,
    public proveedorId?: number,
    public productoId?: number,
    public estatusId?: number
  ) {}
}
