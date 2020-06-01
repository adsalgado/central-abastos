import { GenericService } from './services/generic.service';
import { Overlay } from '@angular/cdk/overlay';
import { NgModule } from '@angular/core';
import { AlertService } from './services/alert.service';
import { HttpService } from './services/http.model.service';
import { LoadingService } from './services/loading-service';
import { LocalStorageEncryptService } from './services/local-storage-encrypt-service';
import { NavParamsService } from './services/nav-params.service';
import { NavService } from './services/nav.service';
import { ValidationService } from './services/validation.service';
import { AutenticateService } from './services/autenticate.service';
import { Event } from './services/event.service';
import { HttpServiceGeneric } from './services/http.generic.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { ChatService } from './shared/chat/chat.service';
import { ProfileService } from './layouts';
import { UsuarioImagenService, UsuarioImagenResolve } from './entities/usuario-imagen';
import { UnidadMedidaService, UnidadMedidaResolve } from './entities/unidad-medida';
import { TransportistaService, TransportistaResolve } from './entities/transportista';
import { TransportistaTarifaService, TransportistaTarifaResolve } from './entities/transportista-tarifa';
import { TipoArticuloService, TipoArticuloResolve } from './entities/tipo-articulo';
import { TipoOfertaResolve, TipoOfertaService } from './entities/tipo-oferta';
import { TarjetaService, TarjetaResolve } from './entities/tarjeta';
import { SeccionResolve, SeccionService } from './entities/seccion';
import { RecolectorService, RecolectorResolve } from './entities/recolector';
import { RecolectorTarifaService, RecolectorTarifaResolve } from './entities/recolector-tarifa';
import { QuejaService, QuejaResolve } from './entities/queja';
import { PromocionService, PromocionResolve } from './entities/promocion';
import { ProveedorService, ProveedorResolve } from './entities/proveedor';
import { ProductoService, ProductoResolve } from './entities/producto';
import { ProductoImagenService, ProductoImagenResolve } from './entities/producto-imagen';
import { PedidoService, PedidoResolve } from './entities/pedido';
import { PedidoDetalleService, PedidoDetalleResolve } from './entities/pedido-detalle';
import { ParametrosAplicacionService, ParametrosAplicacionResolve } from './entities/parametros-aplicacion';
import { PagosService, PagosResolve } from './entities/pagos';
import { OfertaProveedorService, OfertaProveedorResolve } from './entities/oferta-proveedor';
import { NotificacionService, NotificacionResolve } from './entities/notificacion';
import { InventarioService, InventarioResolve } from './entities/inventario';
import { InventarioHistoricoService, InventarioHistoricoResolve } from './entities/inventario-historico';
import { HistoricoPedidoService, HistoricoPedidoResolve } from './entities/historico-pedido';
import { EstatusService, EstatusResolve } from './entities/estatus';
import { EmpresaService, EmpresaResolve } from './entities/empresa';
import { DireccionService, DireccionResolve } from './entities/direccion';
import { ClienteService, ClienteResolve } from './entities/cliente';
import { ChatResolve } from './entities/chat';
import { ChatPrivateService, ChatPrivateResolve } from './entities/chat-private';
import { CategoriaService, CategoriaResolve } from './entities/categoria';
import { CarritoHistoricoService, CarritoHistoricoResolve } from './entities/carrito-historico';
import { CarritoHistoricoDetalleService, CarritoHistoricoDetalleResolve } from './entities/carrito-historico-detalle';
import { CarritoCompraService, CarritoCompraResolve } from './entities/carrito-compra';
import { AdjuntoService, AdjuntoResolve } from './entities/adjunto';
import { ProveedorTransportistaResolve } from './entities/proveedor-transportista';

import {
  UserService,
  JhiTrackerService,
  LoginService,
  LoginModalService,
  JhiLanguageHelper,
  UserRouteAccessService,
  StateStorageService,
  CSRFService,
  AuthServerProvider,
  AccountService
} from './core';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import {
  UserMgmtResolve,
  UserResolve,
  JhiMetricsService,
  LogsService,
  JhiHealthService,
  JhiConfigurationService,
  AuditsService
} from './admin';
import { Register, PasswordService, PasswordResetInitService, PasswordResetFinishService, ActivateService } from './account';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { RequestInterceptorService } from './interceptors/request-interceptor.service';
import { AuthService } from './services/auth.service';
import { MessagingService } from './services/firebase.service';
import { AsyncPipe, CurrencyPipe } from '@angular/common';
import { QRCodeModule } from 'angularx-qrcode';

@NgModule({
  providers: [
    ValidationService,
    NavParamsService,
    LocalStorageEncryptService,
    LoadingService,
    Overlay,
    HttpService,
    HttpServiceGeneric,
    AlertService,
    NavService,
    AutenticateService,
    Event,
    ChatService,
    ProfileService,
    UsuarioImagenService,
    UsuarioImagenResolve,
    UnidadMedidaService,
    UnidadMedidaResolve,
    TransportistaService,
    TransportistaResolve,
    TransportistaTarifaService,
    TransportistaTarifaResolve,
    TipoOfertaService,
    TipoOfertaResolve,
    TipoArticuloService,
    TipoArticuloResolve,
    TarjetaService,
    TarjetaResolve,
    SeccionService,
    SeccionResolve,
    RecolectorService,
    RecolectorResolve,
    RecolectorTarifaService,
    RecolectorTarifaResolve,
    QuejaService,
    QuejaResolve,
    ProveedorService,
    ProveedorResolve,
    PromocionService,
    PromocionResolve,
    ProductoService,
    ProductoResolve,
    ProductoImagenService,
    ProductoImagenResolve,
    ProveedorTransportistaResolve,
    PedidoService,
    PedidoResolve,
    PedidoResolve,
    PedidoDetalleService,
    PedidoDetalleResolve,
    ParametrosAplicacionService,
    ParametrosAplicacionResolve,
    PagosService,
    PagosResolve,
    OfertaProveedorService,
    OfertaProveedorResolve,
    NotificacionService,
    NotificacionResolve,
    InventarioService,
    InventarioResolve,
    InventarioHistoricoService,
    InventarioHistoricoResolve,
    HistoricoPedidoService,
    HistoricoPedidoResolve,
    EstatusService,
    EstatusResolve,
    EmpresaService,
    EmpresaResolve,
    DireccionService,
    DireccionResolve,
    ClienteService,
    ClienteResolve,
    ChatService,
    ChatResolve,
    ChatPrivateService,
    ChatPrivateResolve,
    CategoriaService,
    CategoriaResolve,
    CarritoHistoricoService,
    CarritoHistoricoResolve,
    CarritoHistoricoDetalleService,
    CarritoHistoricoDetalleResolve,
    CarritoCompraService,
    CarritoCompraResolve,
    AdjuntoService,
    AdjuntoResolve,
    UserService,
    JhiTrackerService,
    LoginService,
    LoginModalService,
    JhiLanguageHelper,
    UserRouteAccessService,
    StateStorageService,
    CSRFService,
    AuthServerProvider,
    AccountService,
    PaginationConfig,
    UserMgmtResolve,
    UserResolve,
    JhiMetricsService,
    LogsService,
    JhiHealthService,
    JhiConfigurationService,
    AuditsService,
    Register,
    PasswordService,
    PasswordResetInitService,
    PasswordResetFinishService,
    ActivateService,
    AuthService,
    GenericService,
    CurrencyPipe,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: RequestInterceptorService,
      multi: true
    },
    /* {
      provide: HTTP_INTERCEPTORS,
      useClass: NotificationInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthExpiredInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }, */
    NgbActiveModal,
    MessagingService,
    AsyncPipe,
    QRCodeModule
  ]
})
export class ProvidersModule {}
