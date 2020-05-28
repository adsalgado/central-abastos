import { IProducto } from 'app/shared/model/producto.model';
import { IAdjunto } from './adjunto.model';

export interface ISeccion {
  id?: number;
  nombre?: string;
  descripcion?: string;
  icono?: string;
  productos?: IProducto[];
  empresaId?: number;
  adjuntoId?: number;
  adjunto?: IAdjunto;
}

export class Seccion implements ISeccion {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public icono?: string,
    public productos?: IProducto[],
    public empresaId?: number,
    public adjuntoId?: number,
    public adjunto?: IAdjunto
  ) {}
}
