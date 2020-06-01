import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'producto-proveedor',
        loadChildren: () => import('./producto-proveedor/producto-proveedor.module').then(m => m.AbastosProductoProveedorModule)
      },
      {
        path: 'proveedor-transportista',
        loadChildren: () =>
          import('./proveedor-transportista/proveedor-transportista.module').then(m => m.AbastosProveedorTransportistaModule)
      },
      {
        path: 'producto',
        loadChildren: () => import('./producto/producto.module').then(m => m.AbastosProductoModule)
      },
      {
        path: 'unidad-medida',
        loadChildren: () => import('./unidad-medida/unidad-medida.module').then(m => m.AbastosUnidadMedidaModule)
      },
      {
        path: 'tipo-articulo',
        loadChildren: () => import('./tipo-articulo/tipo-articulo.module').then(m => m.AbastosTipoArticuloModule)
      },
      {
        path: 'categoria',
        loadChildren: () => import('./categoria/categoria.module').then(m => m.AbastosCategoriaModule)
      },
      {
        path: 'seccion',
        loadChildren: () => import('./seccion/seccion.module').then(m => m.AbastosSeccionModule)
      },
      {
        path: 'promocion',
        loadChildren: () => import('./promocion/promocion.module').then(m => m.AbastosPromocionModule)
      },
      {
        path: 'estatus',
        loadChildren: () => import('./estatus/estatus.module').then(m => m.AbastosEstatusModule)
      },
      {
        path: 'adjunto',
        loadChildren: () => import('./adjunto/adjunto.module').then(m => m.AbastosAdjuntoModule)
      },
      {
        path: 'proveedor',
        loadChildren: () => import('./proveedor/proveedor.module').then(m => m.AbastosProveedorModule)
      },
      {
        path: 'oferta-proveedor',
        loadChildren: () => import('./oferta-proveedor/oferta-proveedor.module').then(m => m.AbastosOfertaProveedorModule)
      },
      {
        path: 'tipo-oferta',
        loadChildren: () => import('./tipo-oferta/tipo-oferta.module').then(m => m.AbastosTipoOfertaModule)
      },
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then(m => m.AbastosClienteModule)
      },
      {
        path: 'tarjeta',
        loadChildren: () => import('./tarjeta/tarjeta.module').then(m => m.AbastosTarjetaModule)
      },
      {
        path: 'direccion',
        loadChildren: () => import('./direccion/direccion.module').then(m => m.AbastosDireccionModule)
      },
      {
        path: 'carrito-compra',
        loadChildren: () => import('./carrito-compra/carrito-compra.module').then(m => m.AbastosCarritoCompraModule)
      },
      {
        path: 'carrito-historico',
        loadChildren: () => import('./carrito-historico/carrito-historico.module').then(m => m.AbastosCarritoHistoricoModule)
      },
      {
        path: 'carrito-historico-detalle',
        loadChildren: () =>
          import('./carrito-historico-detalle/carrito-historico-detalle.module').then(m => m.AbastosCarritoHistoricoDetalleModule)
      },
      {
        path: 'pedido',
        loadChildren: () => import('./pedido/pedido.module').then(m => m.AbastosPedidoModule)
      },
      {
        path: 'pedido-detalle',
        loadChildren: () => import('./pedido-detalle/pedido-detalle.module').then(m => m.AbastosPedidoDetalleModule)
      },
      {
        path: 'historico-pedido',
        loadChildren: () => import('./historico-pedido/historico-pedido.module').then(m => m.AbastosHistoricoPedidoModule)
      },
      {
        path: 'transportista',
        loadChildren: () => import('./transportista/transportista.module').then(m => m.AbastosTransportistaModule)
      },
      {
        path: 'transportista-tarifa',
        loadChildren: () => import('./transportista-tarifa/transportista-tarifa.module').then(m => m.AbastosTransportistaTarifaModule)
      },
      {
        path: 'recolector',
        loadChildren: () => import('./recolector/recolector.module').then(m => m.AbastosRecolectorModule)
      },
      {
        path: 'recolector-tarifa',
        loadChildren: () => import('./recolector-tarifa/recolector-tarifa.module').then(m => m.AbastosRecolectorTarifaModule)
      },
      {
        path: 'chat',
        loadChildren: () => import('./chat/chat.module').then(m => m.AbastosChatModule)
      },
      {
        path: 'inventario',
        loadChildren: () => import('./inventario/inventario.module').then(m => m.AbastosInventarioModule)
      },
      {
        path: 'inventario-historico',
        loadChildren: () => import('./inventario-historico/inventario-historico.module').then(m => m.AbastosInventarioHistoricoModule)
      },
      {
        path: 'notificacion',
        loadChildren: () => import('./notificacion/notificacion.module').then(m => m.AbastosNotificacionModule)
      },
      {
        path: 'pagos',
        loadChildren: () => import('./pagos/pagos.module').then(m => m.AbastosPagosModule)
      },
      {
        path: 'queja',
        loadChildren: () => import('./queja/queja.module').then(m => m.AbastosQuejaModule)
      },
      {
        path: 'producto-imagen',
        loadChildren: () => import('./producto-imagen/producto-imagen.module').then(m => m.AbastosProductoImagenModule)
      },
      {
        path: 'parametros-aplicacion',
        loadChildren: () => import('./parametros-aplicacion/parametros-aplicacion.module').then(m => m.AbastosParametrosAplicacionModule)
      },
      {
        path: 'empresa',
        loadChildren: () => import('./empresa/empresa.module').then(m => m.AbastosEmpresaModule)
      },
      {
        path: 'usuario-imagen',
        loadChildren: () => import('./usuario-imagen/usuario-imagen.module').then(m => m.AbastosUsuarioImagenModule)
      },
      {
        path: 'chat-private',
        loadChildren: () => import('./chat-private/chat-private.module').then(m => m.AbastosChatPrivateModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosEntityModule {}
