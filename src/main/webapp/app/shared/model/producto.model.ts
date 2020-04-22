import { Moment } from 'moment';
import { IOfertaProveedor } from 'app/shared/model/oferta-proveedor.model';
import { ICarritoCompra } from 'app/shared/model/carrito-compra.model';
import { ICarritoHistoricoDetalle } from 'app/shared/model/carrito-historico-detalle.model';
import { IPedidoDetalle } from 'app/shared/model/pedido-detalle.model';
import { IInventario } from 'app/shared/model/inventario.model';
import { IProductoImagen } from 'app/shared/model/producto-imagen.model';

export interface IProducto {
  id?: number;
  nombre?: string;
  descripcion?: string;
  caracteristicas?: string;
  precioSinIva?: number;
  precio?: number;
  fechaAlta?: Moment;
  fechaModificacion?: Moment;
  adjuntoId?: number;
  usuarioAltaId?: number;
  usuarioModificacionId?: number;
  ofertaProveedors?: IOfertaProveedor[];
  carritoCompras?: ICarritoCompra[];
  carritoCompraDetalles?: ICarritoHistoricoDetalle[];
  pedidoDetalles?: IPedidoDetalle[];
  inventarios?: IInventario[];
  productoImagens?: IProductoImagen[];
  proveedorId?: number;
  tipoArticuloId?: number;
  categoriaId?: number;
  seccionId?: number;
  estatusId?: number;
  unidadMedidaId?: number;
  empresaId?: number;
}

export class Producto implements IProducto {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public caracteristicas?: string,
    public precioSinIva?: number,
    public precio?: number,
    public fechaAlta?: Moment,
    public fechaModificacion?: Moment,
    public adjuntoId?: number,
    public usuarioAltaId?: number,
    public usuarioModificacionId?: number,
    public ofertaProveedors?: IOfertaProveedor[],
    public carritoCompras?: ICarritoCompra[],
    public carritoCompraDetalles?: ICarritoHistoricoDetalle[],
    public pedidoDetalles?: IPedidoDetalle[],
    public inventarios?: IInventario[],
    public productoImagens?: IProductoImagen[],
    public proveedorId?: number,
    public tipoArticuloId?: number,
    public categoriaId?: number,
    public seccionId?: number,
    public estatusId?: number,
    public unidadMedidaId?: number,
    public empresaId?: number
  ) {}
}
