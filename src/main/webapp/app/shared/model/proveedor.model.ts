import { Moment } from 'moment';
import { IProducto } from 'app/shared/model/producto.model';
import { IOfertaProveedor } from 'app/shared/model/oferta-proveedor.model';
import { IInventario } from 'app/shared/model/inventario.model';

export interface IProveedor {
  id?: number;
  empresaId?: number;
  usuarioId?: number;
  nombre?: string;
  transportistaId?: number;
  fechaAlta?: Moment;
  fechaModificacion?: Moment;
  usuarioAltaId?: number;
  usuarioModificacionId?: number;
}

export class Proveedor implements IProveedor {
  constructor(
    public id?: number,
    public empresaId?: number,
    public usuarioId?: number,
    public nombre?: string,
    public transportistaId?: number,
    public fechaAlta?: Moment,
    public fechaModificacion?: Moment,
    public usuarioAltaId?: number,
    public usuarioModificacionId?: number
  ) {}
}
