import { ICliente } from 'app/shared/model/cliente.model';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { IRecolector } from 'app/shared/model/recolector.model';
import { ITransportista } from 'app/shared/model/transportista.model';
import { IProducto } from 'app/shared/model/producto.model';
import { ICategoria } from 'app/shared/model/categoria.model';
import { ISeccion } from 'app/shared/model/seccion.model';

export interface IEmpresa {
  id?: number;
  nombre?: string;
  clientes?: ICliente[];
  proveedors?: IProveedor[];
  recolectors?: IRecolector[];
  transportistas?: ITransportista[];
  productos?: IProducto[];
  categorias?: ICategoria[];
  seccions?: ISeccion[];
}

export class Empresa implements IEmpresa {
  constructor(
    public id?: number,
    public nombre?: string,
    public clientes?: ICliente[],
    public proveedors?: IProveedor[],
    public recolectors?: IRecolector[],
    public transportistas?: ITransportista[],
    public productos?: IProducto[],
    public categorias?: ICategoria[],
    public seccions?: ISeccion[]
  ) {}
}
