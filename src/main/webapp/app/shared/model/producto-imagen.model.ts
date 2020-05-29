import { Moment } from 'moment';
import { IAdjunto } from './adjunto.model';
import { IProductoProveedor } from './producto-proveedor.model';

export interface IProductoImagen {
  id?: number;
  productoProveedorId?: number;
  productoProveedor?: IProductoProveedor;
  adjuntoId?: number;
  adjunto?: IAdjunto;
}

export class ProductoImagen implements IProductoImagen {
  constructor(
    public id?: number,
    public productoProveedorId?: number,
    public productoProveedor?: IProductoProveedor,
    public adjuntoId?: number,
    public adjunto?: IAdjunto
  ) {}
}
