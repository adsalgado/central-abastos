import { Left } from './directives/left';
import { ChatPage } from './pages/chat/chat';
import { HistorialPedidosPage } from './pages/historial-pedidos/historial-pedidos';
import { HistorialPedidosDetailPage } from './pages/historial-pedidos-detail/historial-pedidos-detail';
import { ProblemasPedidoPage } from './pages/problemas-pedido/problemas-pedido';
import { QrPage } from './pages/qr/qr';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { ControlMessagesComponent } from './components/control-messages/control-messages.component';
import { SpinnerOverlayComponent } from './components/spinner-overlay/spinner-overlay.component';
import { SpinnerComponent } from './components/spinner/spinner.component';
import { LongPress } from './directives/long-press';
import { LoginLayoutComponent } from './pages/login-layout/login-layout.component';
import { LoginComponent } from './pages/login/login.component';
import { ViewModule } from './view.module';
import { HeaderComponent } from './components/header/header.component';
import { HomePublicComponent } from './pages/home-layout/home-public.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { NavbarComponentMain } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { ConfigurationComponent } from './components/configuration/configuration.component';
import { ContainerComponent } from './components/container/container.component';
import { HomeComponent } from './pages/home/home.component';
import { HeaderWithoutMenuComponent } from './components/header-without-menu/header-without-menu.component';
import { JhiMainComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponentJHI, NavbarComponent } from './layouts';
import {
  UserMgmtComponent,
  AuditsComponent,
  JhiConfigurationComponent,
  JhiDocsComponent,
  JhiHealthCheckComponent,
  LogsComponent,
  JhiTrackerComponent,
  UserMgmtDetailComponent,
  UserMgmtUpdateComponent,
  JhiMetricsMonitoringComponent,
  UserMgmtDeleteDialogComponent,
  JhiHealthModalComponent
} from './admin';
import { HomePublicMainComponent } from './pages/home-public/home-public-main.component';
import { ProductoDeleteDialogComponent, ProductoDeletePopupComponent } from './entities/producto';
import { CategoriaPage } from './pages/categoria/categoria';
import { DetalleProductoPage } from './pages/detalle-producto/detalle-producto';
import { MapaProveedoresPage } from './pages/mapa-proveedores/mapa-proveedores';
import { ComparaPreciosProveedorPage } from './pages/compara-precios-proveedor/compara-precios-proveedor';
import { ArticuloProveedoresPage } from './pages/articulo-proveedores/articulo-proveedores';
import { CarritoComprasPage } from './pages/carrito-compras/carrito-compras';
import { DireccionesPage } from './pages/direcciones/direcciones';
import { HomeGeoProveedoresPage } from './pages/home-geo-proveedores/home-geo-proveedores';
import { ListaChatPage } from './pages/lista-chat/lista-chat';
import { PedidosDetailPage } from './pages/pedidos-detail/pedidos-detail';
import { PerfilPage } from './pages/perfil/perfil';
import { ListaCarritoComprasPage } from './pages/lista-carrito-compras/lista-carrito-compras';
import { CarritoHistoricoPage } from './pages/carrito-historico/carrito-historico';
import { ProveedorPage } from './pages/proveedores/recuperar-password';
import { AcercaDePage } from './pages/acerca-de/acerca-de';
import { InfoPage } from './pages/info/info';
import { ContactPage } from './pages/contact/contact';
import { TerminosCondicionesPage } from './pages/terminos-condiciones/terminos-condiciones';
import { Bottom } from './directives/bottom';
import { Right } from './directives/right';
import { Top } from './directives/top';
import { FabButton } from 'ionic-angular';
import { DetalleTarjetaPage } from './pages/detalle-tarjeta/detalle-tarjeta';
import { TarjetasFrecuentesPage } from './pages/tarjetas-frecuentes/tarjetas-frecuentes';
import { FabComponent } from './components/fab-button/fab.component';
import { RegistroPage } from './pages/registro/registro';
import { DocumentosPage } from './pages-proveedor/documentos/documentos';
import { HomeProveedorPage } from './pages-proveedor/home-proveedor/home-proveedor';
import { VerProductosPage } from './pages-proveedor/ver-productos/ver-productos';
import { CalificacionPage } from './pages/calificacion/calificacion';
import { TableModule } from 'primeng/table';
import { Timeline } from './pages/timeline/timeline';
//import { JhiMainComponent, NavbarComponent, FooterComponentJHI, PageRibbonComponent, ActiveMenuDirective, ErrorComponent } from './layouts';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LoginLayoutComponent,
    ControlMessagesComponent,
    SpinnerOverlayComponent,
    SpinnerComponent,
    LongPress,
    HeaderComponent,
    HomePublicComponent,
    SidebarComponent,
    NavbarComponentMain,
    FooterComponent,
    ConfigurationComponent,
    ContainerComponent,
    HomeComponent,
    HeaderWithoutMenuComponent,
    HomePublicMainComponent,
    NavbarComponent,
    ErrorComponent,
    PageRibbonComponent,
    ActiveMenuDirective,
    FooterComponent,
    CategoriaPage,
    DetalleProductoPage,
    MapaProveedoresPage,
    ComparaPreciosProveedorPage,
    ArticuloProveedoresPage,
    CarritoComprasPage,
    HomeGeoProveedoresPage,
    DireccionesPage,

    QrPage,
    ProblemasPedidoPage,
    VerProductosPage,
    HistorialPedidosDetailPage,
    HistorialPedidosPage,

    ListaChatPage,
    ChatPage,
    PedidosDetailPage,

    PerfilPage,
    ListaCarritoComprasPage,
    CarritoHistoricoPage,
    ProveedorPage,

    AcercaDePage,
    InfoPage,
    ContactPage,
    TerminosCondicionesPage,

    Bottom,
    Left,
    Right,
    Top,
    FabComponent,

    DetalleTarjetaPage,
    TarjetasFrecuentesPage,
    RegistroPage,

    DocumentosPage,
    HomeProveedorPage,
    CalificacionPage,
    JhiMainComponent,
    FooterComponentJHI,
    Timeline

    // UserMgmtComponent,
    /*AuditsComponent,
    JhiConfigurationComponent,
    JhiDocsComponent,
    JhiHealthCheckComponent,
    LogsComponent,
    JhiTrackerComponent,
    UserMgmtDetailComponent,
    UserMgmtUpdateComponent,
    JhiMetricsMonitoringComponent,
    UserMgmtDeleteDialogComponent, */
  ],

  imports: [ViewModule],
  exports: [
    AppComponent,
    LoginComponent,
    LoginLayoutComponent,
    ControlMessagesComponent,
    SpinnerOverlayComponent,
    SpinnerComponent,
    LongPress,
    HeaderComponent,
    HomePublicComponent,
    SidebarComponent,
    NavbarComponentMain,
    FooterComponent,
    ConfigurationComponent,
    ContainerComponent,
    HomeComponent,
    HeaderWithoutMenuComponent,
    HomePublicMainComponent,
    ErrorComponent,
    PageRibbonComponent,
    ActiveMenuDirective,
    FooterComponent,
    CategoriaPage,
    DetalleProductoPage,
    MapaProveedoresPage,
    ComparaPreciosProveedorPage,
    ArticuloProveedoresPage,
    CarritoComprasPage,
    HomeGeoProveedoresPage,
    DireccionesPage,

    QrPage,
    ProblemasPedidoPage,
    VerProductosPage,
    HistorialPedidosDetailPage,
    HistorialPedidosPage,

    ListaChatPage,
    ChatPage,
    PedidosDetailPage,

    PerfilPage,
    ListaCarritoComprasPage,
    CarritoHistoricoPage,
    ProveedorPage,
    AcercaDePage,
    InfoPage,
    ContactPage,
    TerminosCondicionesPage,

    Bottom,
    Left,
    Right,
    Top,
    FabComponent,

    DetalleTarjetaPage,
    TarjetasFrecuentesPage,

    RegistroPage,

    DocumentosPage,
    HomeProveedorPage,
    CalificacionPage,
    Timeline
    // UserMgmtComponent,
    /*AuditsComponent,
    JhiConfigurationComponent,
    JhiDocsComponent,
    JhiHealthCheckComponent,
    LogsComponent,
    JhiTrackerComponent,
    UserMgmtDetailComponent,
    UserMgmtUpdateComponent,
    JhiMetricsMonitoringComponent,
    UserMgmtDeleteDialogComponent, */
  ],
  entryComponents: [
    //Producto,
    SpinnerOverlayComponent
  ]
})
export class ComponentsModule {}
