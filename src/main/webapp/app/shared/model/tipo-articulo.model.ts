import { IProducto } from 'app/shared/model/producto.model';

export interface ITipoArticulo {
  id?: number;
  nombre?: string;
  productos?: IProducto[];
}

export class TipoArticulo implements ITipoArticulo {
  constructor(public id?: number, public nombre?: string, public productos?: IProducto[]) {}
}
