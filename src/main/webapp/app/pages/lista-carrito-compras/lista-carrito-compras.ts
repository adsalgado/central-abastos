import { GenericService } from './../../services/generic.service';
import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../../environments/environment.prod';
import * as moment from 'moment';
import { AlertService } from 'app/services/alert.service';
import { LoadingService } from 'app/services/loading-service';
import { NavParamsService } from 'app/services/nav-params.service';

@Component({
  selector: 'page-lista-carrito-compras',
  templateUrl: 'lista-carrito-compras.html',
  styleUrls: ['./lista-carrito-compras.scss']
})
export class ListaCarritoComprasPage {
  public listas: any[] = [];
  public renderSlide: boolean = true;

  constructor(
    private genericService: GenericService,
    private alertaService: AlertService,
    private loadingService: LoadingService,
    private navCtrl: NavParamsService
  ) {
    this.cargarListas();
  }

  ionViewWillLeave() {
    let claseTabs: any = document.getElementsByClassName('tabbar');
    //claseTabs[0].style.display = "flex";
  }

  /**Método para cargar productos en base a especificaciones */
  cargarListas() {
    this.genericService.sendGetRequest(environment.carritoHistorico).subscribe(
      (response: any) => {
        //quitar
        this.listas = response;
        this.listas.forEach(item => {
          if (item.fechaAlta) {
            let stringF: any = item.fechaAlta.split('T');
            let fechaF: any = `${stringF[0]} ${stringF[1]}`;

            moment.locale('ES');
            item.fecha = moment(fechaF, 'YYYY-MM-DD HH:mm:ss').format('D [de] MMMM [de] YYYY HH:mm:ss');
          }
        });
        this.renderSlide = false;
        if (this.listas.length <= 0) {
          this.alertaService.warn('Aún no cuentas con listas frecuentes');
          this.navCtrl.push('main/public-home');
        }
      },
      (error: HttpErrorResponse) => {
        let err: any = error.error;
        this.renderSlide = false;
        //this.alertaService.errorAlertGeneric(err.message ? err.message : "Ocurrió un error en el servicio, intenta nuevamente");
      }
    );
  }

  ionViewDidLoad() {}

  borrar(item: any) {
    let position: any = this.listas.findIndex(img => {
      return img.id == item.id;
    });

    this.loadingService.show();
    this.genericService.sendDelete(`${environment.carritoHistorico}/${item.id}`).subscribe(
      (response: any) => {
        this.listas = [...this.listas.slice(0, position), ...this.listas.slice(position + 1)];
        this.loadingService.hide();
      },
      (error: HttpErrorResponse) => {
        this.loadingService.hide();
        this.alertaService.errorAlertGeneric('No se ha podido eliminar tu lista, intenta nuevamente');
      }
    );
  }

  view(lista: any) {
    this.navCtrl.push('main/carrito-historico', { lista });
  }
}
