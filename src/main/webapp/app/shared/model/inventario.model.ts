import { IInventarioHistorico } from 'app/shared/model/inventario-historico.model';

export interface IInventario {
  id?: number;
  total?: number;
  inventarioHistoricos?: IInventarioHistorico[];
  proveedorId?: number;
  productoId?: number;
}

export class Inventario implements IInventario {
  constructor(
    public id?: number,
    public total?: number,
    public inventarioHistoricos?: IInventarioHistorico[],
    public proveedorId?: number,
    public productoId?: number
  ) {}
}
