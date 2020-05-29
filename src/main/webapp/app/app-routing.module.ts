import { ListaCarritoComprasPage } from './pages/lista-carrito-compras/lista-carrito-compras';
import { ChatPage } from './pages/chat/chat';
import { ProblemasPedidoPage } from './pages/problemas-pedido/problemas-pedido';
import { QrPage } from './pages/qr/qr';
import { HistorialPedidosDetailPage } from './pages/historial-pedidos-detail/historial-pedidos-detail';
import { HistorialPedidosPage } from './pages/historial-pedidos/historial-pedidos';
import { MapaProveedoresPage } from './pages/mapa-proveedores/mapa-proveedores';
import { CategoriaPage } from './pages/categoria/categoria';
import { ProductoComponent } from 'app/entities/producto/producto.component';
import { UserMgmtComponent } from 'app/admin/user-management/user-management.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { errorRoute, navbarRoute } from './layouts';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { HomeComponent } from './home';
import { HomePublicComponent } from './pages/home-layout/home-public.component';
import { LoginComponent } from './pages/login/login.component';
import { HomePublicMainComponent } from './pages/home-public/home-public-main.component';
import { UserRouteAccessService } from './core';
import { auditsRoute, configurationRoute, docsRoute, healthRoute, logsRoute, trackerRoute, userMgmtRoute, metricsRoute } from './admin';
import { JhiLoginModalComponent } from './shared/login/login.component';
import { PasswordResetFinishComponent } from './account/password-reset/finish/password-reset-finish.component';
import { DetalleProductoPage } from './pages/detalle-producto/detalle-producto';
import { ComparaPreciosProveedorPage } from './pages/compara-precios-proveedor/compara-precios-proveedor';
import { ArticuloProveedoresPage } from './pages/articulo-proveedores/articulo-proveedores';
import { CarritoComprasPage } from './pages/carrito-compras/carrito-compras';
import { DireccionesPage } from './pages/direcciones/direcciones';
import { HomeGeoProveedoresPage } from './pages/home-geo-proveedores/home-geo-proveedores';
import { VerProductosPage } from './pages/ver-productos/ver-productos';
import { ListaChatPage } from './pages/lista-chat/lista-chat';
import { PedidosDetailPage } from './pages/pedidos-detail/pedidos-detail';
import { PerfilPage } from './pages/perfil/perfil';
import { CarritoHistoricoPage } from './pages/carrito-historico/carrito-historico';
import { ProveedorPage } from './pages/proveedores/recuperar-password';
import { AcercaDePage } from './pages/acerca-de/acerca-de';
import { InfoPage } from './pages/info/info';
import { ContactPage } from './pages/contact/contact';
import { TerminosCondicionesPage } from './pages/terminos-condiciones/terminos-condiciones';
import { TarjetasFrecuentesPage } from './pages/tarjetas-frecuentes/tarjetas-frecuentes';
import { DetalleTarjetaPage } from './pages/detalle-tarjeta/detalle-tarjeta';
import { RegistroPage } from './pages/registro/registro';
import { DocumentosPage } from './pages-proveedor/documentos/documentos';
import { HomeProveedorPage } from './pages-proveedor/home-proveedor/home-proveedor';

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];
const ADMIN_ROUTES = [auditsRoute, configurationRoute, docsRoute, healthRoute, logsRoute, trackerRoute, ...userMgmtRoute, metricsRoute];
const routes: Routes = [
  { path: '', redirectTo: 'main/public-home', data: { title: 'Home' }, pathMatch: 'full' },
  { path: 'main', redirectTo: 'main/public-home', data: { title: 'Home' }, pathMatch: 'full' },
  { path: 'Home', component: HomeComponent },

  {
    path: 'main',
    component: HomePublicComponent,
    children: [
      { path: 'public-home', component: HomePublicMainComponent },
      //{ path: 'login', component: JhiLoginModalComponent },
      { path: 'home', component: HomeComponent },
      { path: 'users', component: UserMgmtComponent },
      { path: 'categoria', component: CategoriaPage },
      { path: 'detalle', component: DetalleProductoPage },
      { path: 'mapa-proveedores', component: MapaProveedoresPage },
      { path: 'comparativa-proveedores', component: ComparaPreciosProveedorPage },
      { path: 'articulo-proveedor', component: ArticuloProveedoresPage },
      { path: 'carrito-compras', component: CarritoComprasPage },
      { path: 'direcciones', component: DireccionesPage },
      { path: 'proveedores-geo', component: HomeGeoProveedoresPage },
      { path: 'pedidos', component: HistorialPedidosPage },
      { path: 'detalle-pedido', component: HistorialPedidosDetailPage },
      { path: 'detalle-pedido-gral', component: PedidosDetailPage },
      { path: 'qr', component: QrPage },
      { path: 'problemas-pedido', component: ProblemasPedidoPage },
      { path: 'productos', component: VerProductosPage },
      { path: 'perfil', component: PerfilPage },
      { path: 'lista-chat', component: ListaChatPage },
      { path: 'chat', component: ChatPage },

      { path: 'lista-carrito', component: ListaCarritoComprasPage },
      { path: 'carrito-historico', component: CarritoHistoricoPage },

      { path: 'proveedores', component: ProveedorPage },
      { path: 'acercade', component: AcercaDePage },
      { path: 'informacion', component: InfoPage },
      { path: 'contacto', component: ContactPage },
      { path: 'terminos-condiciones', component: TerminosCondicionesPage },

      { path: 'detalle-tarjeta', component: DetalleTarjetaPage },
      { path: 'tarjeta-frecuente', component: TarjetasFrecuentesPage },

      { path: 'direccion-frecuente', component: DireccionesPage },

      { path: 'registro', component: RegistroPage },

      { path: 'documentos-proveedor', component: DocumentosPage },

      { path: 'pedidos-proveedor', component: HomeProveedorPage },
      {
        path: 'admin',
        loadChildren: () => import('./admin/admin.module').then(m => m.AbastosAdminModule)
      },
      {
        path: 'entities',
        loadChildren: () => import('./entities/entity.module').then(m => m.AbastosEntityModule)
      },
      {
        path: 'reset/finish',
        component: PasswordResetFinishComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AbastosAppRoutingModule {}
