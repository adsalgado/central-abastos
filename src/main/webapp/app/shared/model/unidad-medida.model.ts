import { IProducto } from 'app/shared/model/producto.model';

export interface IUnidadMedida {
  id?: number;
  nombre?: string;
  descripcion?: string;
  productos?: IProducto[];
}

export class UnidadMedida implements IUnidadMedida {
  constructor(public id?: number, public nombre?: string, public descripcion?: string, public productos?: IProducto[]) {}
}
