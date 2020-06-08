import { LoadingService } from 'app/services/loading-service';
import { environment } from './../../../environments/environment.prod';
import { Component, OnInit, ElementRef, HostListener, ViewChild, OnDestroy } from '@angular/core';
import { ROUTES } from '../sidebar/sidebar.component';
import { Location, LocationStrategy, PathLocationStrategy } from '@angular/common';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { GenericService } from 'app/services/generic.service';
import { JhiEventManager } from 'ng-jhipster';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
import { NavParamsService } from 'app/services/nav-params.service';
import { AlertService } from 'app/services/alert.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html'
})
export class NavbarComponentMain implements OnInit, OnDestroy {
  private listTitles: any[];
  location: Location;
  mobile_menu_visible: any = 0;
  private toggleButton: any;
  private sidebarVisible: boolean;

  public totalCarrito: any = 0;
  public totalNot: any = 0;

  public user: any = null;

  public miEvents: any = {};

  public show: boolean = false;

  public notificaciones: any[] = [];

  public chatId: any = null;
  @ViewChild('insideElement', null) insideElement;

  public intervalo: any = null;
  constructor(
    location: Location,
    private element: ElementRef,
    private router: Router,
    private genericService: GenericService,
    private events: JhiEventManager,
    private localStorageEncryptService: LocalStorageEncryptService,
    private navParams: NavParamsService,
    private alertaService: AlertService,
    private loadingService: LoadingService
  ) {
    this.location = location;
    this.sidebarVisible = false;
    this.user = this.localStorageEncryptService.getFromLocalStorage('userSession');
    this.getNotifications();
    this.intervalo = setInterval(() => {
      this.getNotifications();
    }, 2500);
    //this.eventManager.broadcast({ name: 'userListModification', content: 'Deleted a user' });

    this.miEvents.uno = this.events.subscribe('intervalando', data => {
      console.log('jejeje');

      try {
        if (this.intervalo) {
          clearInterval(this.intervalo);
          this.intervalo = null;
        }
      } catch (error) {}
    });
  }

  /* @HostListener('document:click', ['$event.target'])
 clickout(event) {
   let clase:any = this;
   window.addEventListener('click', function(e){   
     let mando:any = document.getElementById('cuadro-mando');
     console.log(e.target);
     
     if (clase.getShow() && !mando.contains(e.target)){
       // Clicked in box
     } else{
       console.log("--->");
       clase.activated();
       window.addEventListener("click", null, true);
     }
   });

   
 }  */

  setShow(s) {
    this.show = s;
  }

  getShow() {
    return this.show;
  }

  ngOnInit() {
    this.listTitles = ROUTES.filter(listTitle => listTitle);
    const navbar: HTMLElement = this.element.nativeElement;
    this.toggleButton = navbar.getElementsByClassName('navbar-toggler')[0];
    this.router.events.subscribe(event => {
      this.sidebarClose();
      var $layer: any = document.getElementsByClassName('close-layer')[0];
      if ($layer) {
        $layer.remove();
        this.mobile_menu_visible = 0;
      }
    });

    this.miEvents.uno = this.events.subscribe('totalCarrito', data => {
      try {
        console.log('-------------------->');

        if (data) {
          this.totalCarrito = this.getTotalCarrito(data.content.fromLogin);
        } else {
          this.totalCarrito = this.getTotalCarrito();
        }
      } catch (error) {}
    });

    this.miEvents.dos = this.events.subscribe('totalCarrito2', data => {
      try {
        console.log('<--------------------');
        if (data) {
          this.totalCarrito = this.getTotalCarrito(data.content.fromLogin);
        } else {
          this.totalCarrito = this.getTotalCarrito();
        }
      } catch (error) {
        console.log(error);
      }
    });

    this.miEvents.tres = this.events.subscribe('updateProductos', data => {
      this.getTotalCarrito();
    });

    if (this.user) {
      this.getTotalCarrito();
    }
  }

  activated() {
    this.show = !this.show;
    console.log('->');
  }

  ngOnDestroy() {
    //this.events.destroy(this.miEvents.uno);
    //this.events.destroy(this.miEvents.dos);
    //this.events.destroy(this.miEvents.tres);
  }

  getTotalCarrito(fromLogin: boolean = false) {
    this.genericService.sendGetRequest(environment.carritoCompras).subscribe(
      (response: any) => {
        this.localStorageEncryptService.setToLocalStorage(`${this.user.id_token}`, response);
        this.totalCarrito = response.length;
        console.log(this.totalCarrito);
      },
      (error: HttpErrorResponse) => {}
    );
  }

  a() {
    //if (this.genericService.getTotalCarrito() > 0) {

    //nav.pop();
    this.cargarProductosCarrito();

    //}
  }

  getNotifications() {
    this.genericService.sendGetRequest(environment.notificaciones).subscribe(
      (response: any) => {
        this.notificaciones = response;
        this.totalNot = this.notificaciones.length;
      },
      (error: HttpErrorResponse) => {
        this.totalNot = 0;
        //this.alertaService.warn('Agrega artículos al carrito');
      }
    );
  }

  goToPedido(id: any, goToChat: boolean = false) {
    this.loadingService.show();

    let path: any = `${environment.pedidos}/`;
    if (environment.perfil.activo == 2) {
      path = `${environment.proveedor}/pedidos/`;
    } else if (environment.perfil.activo == 3) {
      path = `${environment.transportista}/pedidos/`;
    }

    this.genericService.sendGetRequest(`${path}${id}`).subscribe(
      (response: any) => {
        if (goToChat) {
          this.activated();
          this.verChat(response);
        } else {
          this.loadingService.hide();
          /* let nav: any = this.app.getActiveNav();
        nav.push(HistorialPedidosDetailPage, { pedido: response }); */
          this.activated();
          this.navParams.push('main/detalle-pedido', { pedido: response });
        }
      },
      (error: HttpErrorResponse) => {
        this.loadingService.hide();
        let err: any = error.error;
        this.alertaService.errorAlertGeneric(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
      }
    );
  }

  goToPedidoCalificar(id: any) {
    this.loadingService.show();

    let path: any = `${environment.pedidos}/`;
    if (environment.perfil.activo == 2 || environment.perfil.activo == 3) {
      path = `${environment.proveedor}/pedidos/`;
    }

    this.genericService.sendGetRequest(`${path}${id}`).subscribe(
      (response: any) => {
        this.loadingService.hide();
        /* let nav: any = this.app.getActiveNav();
      nav.push(CalificacionPage, { pedido: response });
*/
        this.navParams.push('main/calificacion', { pedido: response });
      },
      (error: HttpErrorResponse) => {
        this.loadingService.hide();
        let err: any = error.error;
        this.alertaService.errorAlertGeneric(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
      }
    );
  }

  verChat(pedido: any) {
    switch (environment.perfil.activo) {
      case 1:
        this.genericService.sendGetRequest(`${environment.chats}/${this.chatId}`).subscribe(
          (response: any) => {
            //nav.push(ChatPage, { chat: response, pedido });

            //this.navCtrl.push(ListaChatPage, { chats: this.pedido.pedidoProveedores, pedido: this.pedido });
            this.navParams.push('main/chat', { chat: response, pedido: pedido });
            this.loadingService.hide();
          },
          (error: HttpErrorResponse) => {
            this.loadingService.hide();
            let err: any = error.error;
            this.alertaService.errorAlertGeneric(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
          }
        );

        break;

      case 2:
        this.genericService.sendGetRequest(`${environment.chatsProveedor}${pedido.pedidoProveedores[0].id}/tipoChat/1`).subscribe(
          (response: any) => {
            //nav.push(ChatPage, { chat: response, pedido });
            this.navParams.push('main/chat', { chat: response, pedido: pedido });
            this.loadingService.hide();
          },
          (error: HttpErrorResponse) => {
            this.loadingService.hide();
            let err: any = error.error;
            this.alertaService.errorAlertGeneric(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
          }
        );
        break;
      case 3:
        this.genericService.sendGetRequest(`${environment.chatsProveedor}${pedido.pedidoProveedores[0].id}/tipoChat/2`).subscribe(
          (response: any) => {
            this.navParams.push('main/chat', { chat: response, pedido: pedido });
            //this.navCtrl.push(ChatPage, { chat: response, pedido: this.pedido });
            this.loadingService.hide();
          },
          (error: HttpErrorResponse) => {
            this.loadingService.hide();
            let err: any = error.error;
            this.alertaService.errorAlertGeneric(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
          }
        );
        break;
    }
  }

  open(n: any) {
    this.user = this.localStorageEncryptService.getFromLocalStorage('userSession');
    //window.location.href
    let currentPage: any = window.location.href;
    this.loadingService.show();
    this.genericService.sendDeleteRequest(`${environment.notificaciones}/${n.id}`).subscribe(
      (response: any) => {
        this.loadingService.hide();
        switch (Number(n.viewId)) {
          case 1:
            let pedido: any = null;
            if (n.data) {
              pedido = n.data.pedidoId;
            }
            this.events.broadcast({ name: 'cargarPedidos', content: {} });
            this.goToPedido(Number(pedido));
            break;
          case 10:
            let chatId: any = n.data.chatId;
            let pedidoId: any = n.data.pedidoId;
            this.chatId = chatId;
            this.goToPedido(Number(pedidoId), true);
            break;
          case 2:
            let pedido2: any = n.data.pedidoId;
            let pedidoProveedor: any = n.data.pedidoProveedorId;
            this.goToPedido(Number(pedido2));
            break;
          case 3:
            let pedido3: any = n.data.pedidoId;
            let pedidoProveedor3: any = n.data.pedidoProveedorId;
            this.goToPedido(Number(pedido3));
            break;
          case 6:
            let pedido6: any = n.data.pedidoId;
            let pedidoProveedor6: any = n.data.pedidoProveedorId;
            this.goToPedido(Number(pedido6));
            break;
          case 4:
            let pedido4: any = n.data.pedidoId;
            this.goToPedidoCalificar(Number(pedido4));
            break;
        }
      },
      (error: HttpErrorResponse) => {
        this.loadingService.hide();
        this.alertaService.error('Ocurrió un error intenta nuevamente');
      }
    );
  }

  cargarProductosCarrito() {
    this.genericService.sendGetRequest(environment.carritoCompras).subscribe(
      (response: any) => {
        this.user = this.localStorageEncryptService.getFromLocalStorage('userSession');
        this.localStorageEncryptService.setToLocalStorage(`${this.user.id_token}`, response);
        this.navParams.push('main/carrito-compras');
      },
      (error: HttpErrorResponse) => {
        this.alertaService.warn('Agrega artículos al carrito');
      }
    );
  }

  b() {
    console.log('b');
  }

  sidebarOpen() {
    const toggleButton = this.toggleButton;
    const body = document.getElementsByTagName('body')[0];
    setTimeout(function() {
      toggleButton.classList.add('toggled');
    }, 500);

    body.classList.add('nav-open');

    this.sidebarVisible = true;
  }
  sidebarClose() {
    const body = document.getElementsByTagName('body')[0];
    this.toggleButton.classList.remove('toggled');
    this.sidebarVisible = false;
    body.classList.remove('nav-open');
  }
  sidebarToggle() {
    // const toggleButton = this.toggleButton;
    // const body = document.getElementsByTagName('body')[0];
    var $toggle = document.getElementsByClassName('navbar-toggler')[0];

    if (this.sidebarVisible === false) {
      this.sidebarOpen();
    } else {
      this.sidebarClose();
    }
    const body = document.getElementsByTagName('body')[0];

    if (this.mobile_menu_visible == 1) {
      // $('html').removeClass('nav-open');
      body.classList.remove('nav-open');
      if ($layer) {
        $layer.remove();
      }
      setTimeout(function() {
        $toggle.classList.remove('toggled');
      }, 400);

      this.mobile_menu_visible = 0;
    } else {
      setTimeout(function() {
        $toggle.classList.add('toggled');
      }, 430);

      var $layer = document.createElement('div');
      $layer.setAttribute('class', 'close-layer');

      if (body.querySelectorAll('.main-panel')) {
        document.getElementsByClassName('main-panel')[0].appendChild($layer);
      } else if (body.classList.contains('off-canvas-sidebar')) {
        document.getElementsByClassName('wrapper-full-page')[0].appendChild($layer);
      }

      setTimeout(function() {
        $layer.classList.add('visible');
      }, 100);

      $layer.onclick = function() {
        //asign a function
        body.classList.remove('nav-open');
        this.mobile_menu_visible = 0;
        $layer.classList.remove('visible');
        setTimeout(function() {
          $layer.remove();
          $toggle.classList.remove('toggled');
        }, 400);
      }.bind(this);

      body.classList.add('nav-open');
      this.mobile_menu_visible = 1;
    }
  }

  getTitle() {
    /* let ruta: string = '';
    switch (this.router.url) {
      case '/main/public-home':
        ruta = 'Bienvenid@';
        break;

      default:
        break;
    }
    return ruta; */
  }
}
