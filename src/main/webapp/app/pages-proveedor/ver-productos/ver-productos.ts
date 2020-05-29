import { GenericService } from './../../services/generic.service';
import { Component, OnDestroy } from '@angular/core';
import { environment } from '../../../environments/environment.prod';
import { HttpErrorResponse } from '@angular/common/http';
import { AlertService } from 'app/services/alert.service';
import { NavParamsService } from 'app/services/nav-params.service';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';

@Component({
  selector: 'page-ver-productos',
  templateUrl: 'ver-productos.html',
  styleUrls: ['./ver-productos.scss']
})
export class VerProductosPage implements OnDestroy {
  public pedidos: any = null;

  public env: any = environment;

  public tipoUsuario: any = 0;

  public user: any = null;
  constructor(
    public navParams: NavParamsService,
    private genericService: GenericService,
    private alertaService: AlertService,
    private localStorageEncryptService: LocalStorageEncryptService
  ) {
    this.pedidos = navParams.get('pedidos');
    this.pedidos.pedidoProveedores[0].pedidoDetalles.forEach(element => {
      element.activado = false;
    });
    this.user = this.localStorageEncryptService.getFromLocalStorage('userSession');
    this.tipoUsuario = this.user.tipo_usuario;
    console.log(this.pedidos.pedidoProveedores[0].pedidoDetalles);
  }

  ngOnDestroy() {}

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
      switch (this.tipoUsuario) {
        case 3:
          let body: any = {
            pedidoProveedorId: this.pedidos.pedidoProveedores[0].id,
            estatusId: 13
          };

          this.genericService.sendPutRequest(environment.pedidosProveedores, body).subscribe(
            (response1: any) => {
              this.alertaService.info(`El pedido ha sido confirmado`);
              window.history.back();
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
              window.history.back();
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
