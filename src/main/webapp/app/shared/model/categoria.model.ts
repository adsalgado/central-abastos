import { IProducto } from 'app/shared/model/producto.model';
import { ISeccion } from './seccion.model';
import { IAdjunto } from './adjunto.model';

export interface ICategoria {
  id?: number;
  nombre?: string;
  descripcion?: string;
  icono?: string;
  seccionId?: number;
  seccion?: ISeccion;
  adjuntoId?: number;
  adjunto?: IAdjunto;
}

export class Categoria implements ICategoria {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public icono?: string,
    public seccionId?: number,
    public seccion?: ISeccion,
    public adjuntoId?: number,
    public adjunto?: IAdjunto
  ) {}
}
