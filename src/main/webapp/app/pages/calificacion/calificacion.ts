import { GenericService } from './../../services/generic.service';
import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../../environments/environment.prod';
import { LoadingService } from 'app/services/loading-service';
import { AlertService } from 'app/services/alert.service';
import { NavParamsService } from 'app/services/nav-params.service';

@Component({
  selector: 'page-calificacion',
  templateUrl: 'calificacion.html',
  styleUrls: ['./calificacion.scss']
})
export class CalificacionPage {
  public calificacionActual: string = 'Excelente';

  public stars: any[] = [];

  public queja: string = '';

  public nombre: string = 'Juan López Sarrelangue';

  public pedido: any = null;

  public env: any = environment;
  constructor(
    public navParams: NavParamsService,
    private genericService: GenericService,
    private loadingService: LoadingService,
    private alertaService: AlertService
  ) {
    this.pedido = navParams.get('pedido');
    this.stars.push({
      selected: true,
      id: 1
    });
    this.stars.push({
      selected: true,
      id: 2
    });
    this.stars.push({
      selected: true,
      id: 3
    });
    this.stars.push({
      selected: true,
      id: 4
    });
    this.stars.push({
      selected: true,
      id: 5
    });
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad CalificacionPage');
  }

  selecciona(star: any) {
    this.stars.forEach(element => {
      element.selected = false;
    });

    for (let index = 0; index < this.stars.length; index++) {
      const element = this.stars[index];
      if (element.id <= star.id) {
        element.selected = true;
      }
    }

    switch (star.id) {
      case 1:
        this.calificacionActual = 'Pésimo';
        break;
      case 2:
        this.calificacionActual = 'Malo';
        break;
      case 3:
        this.calificacionActual = 'Regular';
        break;
      case 4:
        this.calificacionActual = 'Bueno';
        break;
      case 5:
        this.calificacionActual = 'Excelente';
        break;
    }
  }

  enviar() {
    this.loadingService.show();
    let cal: any = 1;
    this.stars.forEach(element => {
      if (element.selected) {
        cal = element.id;
      }
    });
    let body: any = {
      pedidoProveedorId: this.pedido.id,
      calificacionServicio: cal,
      comentarios: this.queja
    };
    this.genericService.sendPutRequest(environment.calificacionServicio, body).subscribe(
      (response: any) => {
        this.loadingService.hide();
        //this.navCtrl.pop();
        window.history.back();
        this.alertaService.info('Tu calificación ha sido enviada, gracias.');
      },
      (error: HttpErrorResponse) => {
        this.loadingService.hide();
        this.alertaService.errorAlertGeneric('Ocurrió un error, intenta nuevamente');
      }
    );
  }
}
