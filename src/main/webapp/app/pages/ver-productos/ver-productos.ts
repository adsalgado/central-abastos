import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
import { GenericService } from './../../services/generic.service';
import { Component, OnDestroy } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { environment } from '../../../environments/environment.prod';
import { HttpErrorResponse } from '@angular/common/http';
import { AlertService } from 'app/services/alert.service';

/*@Component({
  selector: 'page-ver-productos',
  templateUrl: 'ver-productos.html',
  styleUrls: ['./ver-productos.scss']
})*/
export class VerProductosPage implements OnDestroy {
  public pedidos: any = null;

  public env: any = environment;
  constructor(
    public navCtrl: NavParams,
    private genericService: GenericService,
    private alertaService: AlertService,
    private localStorageEncryptService: LocalStorageEncryptService
  ) {
    this.pedidos = navCtrl.get('pedidos');
    this.pedidos.pedidoProveedores[0].pedidoDetalles.forEach(element => {
      element.activado = false;
    });

    console.log(this.pedidos.pedidoProveedores[0].pedidoDetalles);
  }

  ionViewDidLoad() {
    let claseTabs: any = document.getElementsByClassName('tabbar');
    claseTabs[0].style.display = 'none';
  }

  ngOnDestroy() {
    let claseTabs: any = document.getElementsByClassName('tabbar');
    claseTabs[0].style.display = 'flex';
  }

  solicitar() {}

  confirmar() {
    let nada: number = 0;
    this.pedidos.pedidoProveedores[0].pedidoDetalles.forEach(element => {
      if (!element.activado) {
        nada++;
      }
    });
    if (nada > 0) {
      this.alertaService.warn('Debe confirmar cada artículo para poder confirmar su pedido');
    } else {
      let user: any = this.localStorageEncryptService.getFromLocalStorage('userSession');
      //if (user.tipo_usuario == 4) {
      switch (user.tipo_usuario) {
        case 3:
          let body: any = {
            pedidoProveedorId: this.pedidos.pedidoProveedores[0].id,
            estatusId: 13
          };

          this.genericService.sendPutRequest(environment.pedidosProveedores, body).subscribe(
            (response1: any) => {
              this.alertaService.info(`El pedido ha sido confirmado`);
              //this.navCtrl.pop();
            },
            (error: HttpErrorResponse) => {
              this.alertaService.errorAlertGeneric('Ocurrió un error, por favor intenta nuevamente.');
            }
          );
          break;
        case 4:
          let body2: any = {
            pedidoProveedorId: this.pedidos.pedidoProveedores[0].id,
            estatusId: 14
          };

          this.genericService.sendPutRequest(environment.pedidosProveedores, body2).subscribe(
            (response1: any) => {
              this.alertaService.info(`El pedido ha sido confirmado`);
              //this.navCtrl.pop();
            },
            (error: HttpErrorResponse) => {
              this.alertaService.errorAlertGeneric('Ocurrió un error, por favor intenta nuevamente.');
            }
          );
          break;
      }
    }
  }
}
