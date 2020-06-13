import { environment } from './../../../environments/environment.prod';
import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { GenericService } from '../../services/generic.service';
import { NavParamsService } from 'app/services/nav-params.service';
import { AlertService } from 'app/services/alert.service';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'page-problemas-pedido',
  templateUrl: 'problemas-pedido.html',
  styleUrls: ['./problemas-pedido.scss']
})
export class ProblemasPedidoPage {
  public pedido: any = null;

  constructor(
    public navCtrl: NavParamsService,
    private genericService: GenericService,
    private localStorageEncryptService: LocalStorageEncryptService,
    private alertaService: AlertService
  ) {
    this.pedido = navCtrl.get('pedidoProblem');
    console.log(this.pedido);
  }

  ionViewDidLoad() {}

  queja() {
    let body: any = {
      pedidoProveedorId: this.pedido.pedidoProveedores[0].id
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
