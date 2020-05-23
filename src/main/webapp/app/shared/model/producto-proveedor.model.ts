import { Moment } from 'moment';
import { IProducto } from './producto.model';
import { IProveedor } from './proveedor.model';
import { IEstatus } from './estatus.model';

export interface IProductoProveedor {
  id?: number;
  precioSinIva?: number;
  precio?: number;
  fechaAlta?: Moment;
  fechaModificacion?: Moment;
  proveedorId?: number;
  productoId?: number;
  estatusId?: number;
  producto?: IProducto;
  proveedor?: IProveedor;
  estatus?: IEstatus;
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
    public estatusId?: number,
    public producto?: IProducto,
    public proveedor?: IProveedor,
    public estatus?: IEstatus
  ) {}
}
