import { Moment } from 'moment';

export interface IProductoImagen {
  id?: number;
  fechaAlta?: Moment;
  usuarioAltaId?: number;
  productoId?: number;
  adjuntoId?: number;
}

export class ProductoImagen implements IProductoImagen {
  constructor(
    public id?: number,
    public fechaAlta?: Moment,
    public usuarioAltaId?: number,
    public productoId?: number,
    public adjuntoId?: number
  ) {}
}
