import { IProducto } from 'app/shared/model/producto.model';

export interface ICategoria {
  id?: number;
  nombre?: string;
  productos?: IProducto[];
  empresaId?: number;
}

export class Categoria implements ICategoria {
  constructor(public id?: number, public nombre?: string, public productos?: IProducto[], public empresaId?: number) {}
}
