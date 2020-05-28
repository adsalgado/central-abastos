import { IProducto } from 'app/shared/model/producto.model';
import { ICategoria } from './categoria.model';
import { IAdjunto } from './adjunto.model';

export interface ITipoArticulo {
  id?: number;
  nombre?: string;
  descripcion?: string;
  categoriaId?: number;
  categoria?: ICategoria;
  adjuntoId?: number;
  adjunto?: IAdjunto;
}

export class TipoArticulo implements ITipoArticulo {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public categoriaId?: number,
    public categoria?: ICategoria,
    public adjuntoId?: number,
    public adjunto?: IAdjunto
  ) {}
}
