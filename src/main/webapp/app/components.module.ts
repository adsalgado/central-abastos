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
    ArticuloProveedoresPage
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
    ArticuloProveedoresPage
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
