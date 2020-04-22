import { Moment } from 'moment';
import { IProducto } from 'app/shared/model/producto.model';
import { IOfertaProveedor } from 'app/shared/model/oferta-proveedor.model';
import { IInventario } from 'app/shared/model/inventario.model';

export interface IProveedor {
  id?: number;
  nombre?: string;
  fechaAlta?: Moment;
  fechaModificacion?: Moment;
  usuarioAltaId?: number;
  usuarioModificacionId?: number;
  productos?: IProducto[];
  ofertaProveedors?: IOfertaProveedor[];
  inventarios?: IInventario[];
  empresaId?: number;
}

export class Proveedor implements IProveedor {
  constructor(
    public id?: number,
    public nombre?: string,
    public fechaAlta?: Moment,
    public fechaModificacion?: Moment,
    public usuarioAltaId?: number,
    public usuarioModificacionId?: number,
    public productos?: IProducto[],
    public ofertaProveedors?: IOfertaProveedor[],
    public inventarios?: IInventario[],
    public empresaId?: number
  ) {}
}
