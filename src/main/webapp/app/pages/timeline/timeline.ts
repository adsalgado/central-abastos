import { GenericService } from './../../services/generic.service';
import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../../environments/environment.prod';
import { LoadingService } from 'app/services/loading-service';
import { AlertService } from 'app/services/alert.service';
import { NavParamsService } from 'app/services/nav-params.service';

@Component({
  selector: 'page-timeline',
  templateUrl: 'timeline.html',
  styleUrls: ['./timeline.scss']
})
export class Timeline {
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
    console.log(this.pedido);
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad CalificacionPage');
  }
}
