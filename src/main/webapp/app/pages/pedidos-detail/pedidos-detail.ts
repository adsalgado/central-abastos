import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { GenericService } from '../../services/generic.service';
import { QrPage } from '../qr/qr';
import { ListaChatPage } from '../lista-chat/lista-chat';
import { HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../../environments/environment.prod';
import { ChatPage } from '../chat/chat';
import { ArticuloProveedoresPage } from '../articulo-proveedores/articulo-proveedores';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
import { AlertService } from 'app/services/alert.service';
import { LoadingService } from 'app/services/loading-service';
import { NavParamsService } from 'app/services/nav-params.service';

@Component({
  selector: 'page-pedidos-detail',
  templateUrl: 'pedidos-detail.html',
  styleUrls: ['./pedidos-detail.scss']
})
export class PedidosDetailPage {
  public detalle: any = null;
  public id: any = null;

  constructor(
    public navCtrl: NavParamsService,
    private genericService: GenericService,
    private localStorageEncryptService: LocalStorageEncryptService,
    private alertaService: AlertService,
    private loadingService: LoadingService
  ) {
    this.detalle = navCtrl.get('detalle');

    console.log(this.detalle);

    console.log(this.detalle);

    this.id = navCtrl.get('id');
  }

  ionViewDidLoad() {}

  terminarServicio(pedido) {
    this.navCtrl.push('main/qr', { pedido });
  }

  verChatProveedor(pedido: any) {
    console.log(this.detalle);

    if (!pedido.chatProveedorid) {
      this.alertaService.warn('El proveedor aun no inicia el chat, espera a que él se comunique contigo');
    } else {
      this.loadingService.show();
      this.genericService.sendGetRequest(`${environment.chats}/${pedido.chatProveedorid}`).subscribe(
        (response: any) => {
          this.navCtrl.push('main/chat', { chat: response, pedido: this.detalle });
          this.loadingService.hide();
        },
        (error: HttpErrorResponse) => {
          this.loadingService.hide();
          let err: any = error.error;
          this.alertaService.errorAlertGeneric(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
        }
      );
    }
  }

  verChatTransportista(pedido: any) {
    if (!pedido.chatTransportistaId) {
      this.alertaService.warn('El transportista aun no inicia el chat, espera a que él se comunique contigo');
    } else {
      this.loadingService.show();
      this.genericService.sendGetRequest(`${environment.chats}/${pedido.chatTransportistaId}`).subscribe(
        (response: any) => {
          this.navCtrl.push('main/chat', { chat: response, pedido: this.detalle });
          this.loadingService.hide();
        },
        (error: HttpErrorResponse) => {
          this.loadingService.hide();
          let err: any = error.error;
          this.alertaService.errorAlertGeneric(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
        }
      );
    }
  }

  verProductos(pedido: any) {
    /* this.loadingService.show().then(() => {
      this.genericService.sendGetRequest(`${environment.proveedorProductos}/proveedor/${pedido.proveedorId}`).subscribe((response: any) => {
        

        this.loadingService.hide();
        this.navCtrl.push(ArticuloProveedoresPage, { productos: response, proveedor : pedido.proveedor });
      }, (error: HttpErrorResponse) => {
        this.loadingService.hide();
        let err: any = error.error;
        this.alertaService.errorAlertGeneric(err.message ? err.message : "Ocurrió un error en el servicio, intenta nuevamente");
      });
    }); */
    let productos: any[] = [];
    pedido.pedidoDetalles.forEach(element => {
      productos.push(element.productoProveedor);
    });
    console.log(productos);

    this.navCtrl.push('main/articulo-proveedor', { productos, proveedor: pedido.proveedor, fromCliente: true });
  }

  calificar(pedido: any) {
    this.navCtrl.push('main/calificacion', { pedido });
  }

  queja(p) {
    let body: any = {
      pedidoProveedorId: p.id
    };

    this.genericService.sendPostRequest(`${environment.queja}`, body).subscribe(
      (response: any) => {
        this.alertaService.success('Un contact center te atenderá en breve');
      },
      (error: HttpErrorResponse) => {
        this.alertaService.error('No se ha podido contactar al administrador, intenta nuevamente');
      }
    );
  }
}
