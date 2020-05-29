import { IProducto } from 'app/shared/model/producto.model';
import { IAdjunto } from './adjunto.model';

export interface IPromocion {
  id?: number;
  titulo?: string;
  descripcion?: string;
  link?: string;
  adjuntoId?: number;
  adjunto?: IAdjunto;
}

export class Promocion implements IPromocion {
  constructor(
    public id?: number,
    public titulo?: string,
    public descripcion?: string,
    public link?: string,
    public adjuntoId?: number,
    public adjunto?: IAdjunto
  ) {}
}
