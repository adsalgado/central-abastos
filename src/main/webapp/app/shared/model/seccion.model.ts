import { IProducto } from 'app/shared/model/producto.model';

export interface ISeccion {
  id?: number;
  nombre?: string;
  productos?: IProducto[];
  empresaId?: number;
}

export class Seccion implements ISeccion {
  constructor(public id?: number, public nombre?: string, public productos?: IProducto[], public empresaId?: number) {}
}
