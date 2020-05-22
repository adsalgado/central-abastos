import { Component, OnInit } from '@angular/core';
import { IonicPage, NavController, NavParams, AlertController, ActionSheetController, Platform } from 'ionic-angular';
import { User } from '../../models/User';
import { GenericService } from '../../services/generic.service';
import { environment } from '../../../environments/environment.prod';
import { HttpErrorResponse, HttpHeaders, HttpClient } from '@angular/common/http';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
import { AlertService } from 'app/services/alert.service';
import { LoadingService } from 'app/services/loading-service';
import { NavParamsService } from 'app/services/nav-params.service';
import swal, { SweetAlertOptions } from 'sweetalert2';

declare var google;

@Component({
  selector: 'page-historial-pedidos-detail',
  templateUrl: 'historial-pedidos-detail.html',
  styleUrls: ['./historial-pedidos-detail.scss']
})
export class HistorialPedidosDetailPage implements OnInit {
  public user: User = null;
  public pedido: any = null;
  public map: any;

  public tipoUsuario: any = environment.perfil.activo;

  public ref: any;

  constructor(
    public navCtrl: NavParamsService,
    private genericService: GenericService,
    private localStorageEncryptService: LocalStorageEncryptService,
    private alertaService: AlertService,
    private loadingService: LoadingService,
    private httpClient: HttpClient
  ) {
    this.user = this.localStorageEncryptService.getFromLocalStorage('userSession');
    this.pedido = navCtrl.get('pedido');
    console.log(this.pedido);
  }

  ngOnInit() {
    this.loadMap();
  }

  loadMap() {
    let latitude = this.pedido.direccionContacto.latitud;
    let longitude = this.pedido.direccionContacto.longitud;

    // create a new map by passing HTMLElement
    let mapEle: HTMLElement = document.getElementById('map_canvas');

    // create LatLng object

    let myLatLng = { lat: Number(latitude), lng: Number(longitude) };

    // create map
    this.map = new google.maps.Map(mapEle, {
      center: myLatLng,
      zoom: 15
    });

    google.maps.event.addListenerOnce(this.map, 'idle', () => {
      let info: any = `<div>Ejemplo de window</div>`;

      let infowindow: any = new google.maps.InfoWindow({
        content: info
      });
      let component: any = this;

      component.marker = new google.maps.Marker({
        position: myLatLng, //{ lat: -0.179041, lng: -78.499211 },
        map: this.map,
        title: 'Hello World!',
        id: 'marcador-1',
        //draggable: true,
        icon: environment.icons['persona'].icon
      });

      component.marker.addListener('click', () => {
        //infowindow.open(this.map, this.marker);
        //component.changeInfoCard();
      });

      /* marker.addEventListener("click", (e: Event) => {
       
        
      }); */

      mapEle.classList.add('show-map');
    });
  }

  verDetalle() {
    this.navCtrl.push('main/detalle-pedido-gral', { detalle: this.pedido.pedidoProveedores, id: this.pedido.id });
  }

  problemasPedido() {
    this.navCtrl.push('main/problemas-pedido');
  }

  verProductos() {
    this.navCtrl.push('main/productos', { pedidos: this.pedido });
  }

  terminarServicio() {
    this.navCtrl.push('main/qr', { pedido: this.pedido });
  }

  enviarPedido() {
    let body: any = {
      pedidoProveedorId: this.pedido.pedidoProveedores[0].id,
      estatusId: 18 //antes 14
    };

    this.genericService.sendPutRequest(environment.pedidosProveedores, body).subscribe(
      (response1: any) => {
        this.alertaService.info(`El pedido se ha enviado al transportista`);
      },
      (error: HttpErrorResponse) => {
        this.alertaService.errorAlertGeneric('Ocurrió un error, por favor intenta nuevamente.');
      }
    );
  }

  verRecibo() {
    if (this.pedido.receiptUrl && this.pedido.receiptUrl.length > 0) {
      //this.returnDocument(this.pedido.folio.toString(),this.pedido.receiptUrl,'application/pdf');
      //this.aceptarRedirect(this.pedido.receiptUrl);
      window.open(this.pedido.receiptUrl);
    } else {
      this.alertaService.warn('No se ha generado el ticket de tu pedido, contacta al administrador');
    }
    //
  }

  completarServicio(tokenEntrada: any) {
    let body: any = {
      pedidoProveedorId: this.pedido.pedidoProveedores[0].id,
      token: tokenEntrada
    };
    this.loadingService.show();
    this.genericService.sendPutRequest(`${environment.pedidosTransportistas}/terminar-servicio`, body).subscribe(
      (response: any) => {
        this.loadingService.hide();
        this.alertaService.info('El servició terminó correctamente');
      },
      (error: HttpErrorResponse) => {
        this.loadingService.hide();
        this.alertaService.errorAlertGeneric('Ocurrió un error, por favor vuelve a intentarlo');
      }
    );
  }

  presentActionSheet() {
    let data: any = {
      title: 'Mi lista frecuente',
      message: `<div><input id="id-texto" class="input-lista"/></div>`
    };

    swal
      .fire({
        title: data.title,
        html: data.message,
        type: 'info',
        showCancelButton: true,
        //confirmButtonColor: '#3085d6',
        //cancelButtonColor: '#d33',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Aceptar'
      })
      .then(result => {
        if (result.value) {
          let id: any = document.getElementById('id-texto');

          this.completarServicio(id.value);
        }
      });
  }

  verMapa() {
    if (environment.perfil.activo == 3) {
      //http://google.com/maps/@?api=1&map_action=map&center=-33.712206,150.311941&zoom=1
      let latitude = this.pedido.direccionContacto.latitud;
      let longitude = this.pedido.direccionContacto.longitud;
      window.open(`https://www.google.com/maps/dir/?api=1&travelmode=driving&layer=traffic&destination=${latitude},${longitude}&zoom=18`);
    }
  }

  verChat() {
    switch (environment.perfil.activo) {
      case 1:
        if (!this.pedido.pedidoProveedores[0].chatProveedorid) {
          this.alertaService.warn('El proveedor aun no inicia el chat, espera a que él se comunique contigo');
        } else {
          /* this.loadingService.show().then(() => {
            this.genericService.sendGetRequest(`${environment.chats}/${this.pedido.pedidoProveedores[0].chatProveedorid}`).subscribe((response: any) => {
              */
          this.navCtrl.push('lista-chat', { chats: this.pedido.pedidoProveedores, pedido: this.pedido });
          /* this.loadingService.hide();
        }, (error: HttpErrorResponse) => {
          this.loadingService.hide();
          let err: any = error.error;
          this.alertaService.errorAlertGeneric(err.message ? err.message : "Ocurrió un error en el servicio, intenta nuevamente");
        });
      }); */
        }
        break;

      case 2:
        this.loadingService.show();
        this.genericService.sendGetRequest(`${environment.chatsProveedor}${this.pedido.pedidoProveedores[0].id}/tipoChat/1`).subscribe(
          (response: any) => {
            this.navCtrl.push('chat', { chat: response, pedido: this.pedido });
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
        this.loadingService.show();
        this.genericService.sendGetRequest(`${environment.chatsProveedor}${this.pedido.pedidoProveedores[0].id}/tipoChat/2`).subscribe(
          (response: any) => {
            this.navCtrl.push('chat', { chat: response, pedido: this.pedido });
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
}
