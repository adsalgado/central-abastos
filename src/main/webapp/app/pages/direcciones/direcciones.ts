import { LocalStorageEncryptService } from './../../services/local-storage-encrypt-service';
import { JhiEventManager } from 'ng-jhipster';
import { HomeGeoProveedoresPage } from './../home-geo-proveedores/home-geo-proveedores';

import { GenericService } from './../../services/generic.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { IonicPage, NavController, NavParams, Events, ViewController } from 'ionic-angular';
import { HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../../environments/environment.prod';
import { AlertService } from 'app/services/alert.service';
import { LoadingService } from 'app/services/loading-service';
import { NavParamsService } from 'app/services/nav-params.service';

@Component({
  selector: 'page-direcciones',
  templateUrl: 'direcciones.html',
  styleUrls: ['./direcciones.scss']
})
export class DireccionesPage implements OnDestroy, OnInit {
  public listaDirecciones: any = [];

  public render: boolean = false;
  public fromPop: boolean = false;

  public dataEvents: any = {};
  constructor(
    public navCtrl: NavParamsService,
    private genericService: GenericService,
    private alertaService: AlertService,
    private loadingService: LoadingService,
    private events: JhiEventManager,
    private localStorageEncryptService: LocalStorageEncryptService
  ) {
    this.fromPop = navCtrl.get('fromPop');
    this.cargarDireccionesLista();

    this.dataEvents.uno = this.events.subscribe('direction', data => {
      if (!data.create) {
        let position = this.listaDirecciones.findIndex(img => {
          return img.id == data.body.id;
        });
        for (let index = 0; index < this.listaDirecciones.length; index++) {
          const element = this.listaDirecciones[index];
          if (element.id == data.body.id) {
            position = index;
          }
        }
        this.listaDirecciones[position] = data.body;
      } else {
        this.listaDirecciones.push(data.body);
      }
      //this.cards = this.localStorageEncryptService.getFromLocalStorage(`${this.user.id_token}-cards`);
    });

    this.dataEvents.dos = this.events.subscribe('actualizarTarjetas', data => {
      this.cargarDireccionesLista(data.fromLogin);
    });
  }

  ionViewDidLoad() {}

  ngOnInit() {}

  ngOnDestroy() {
    this.events.destroy(this.dataEvents.uno);
    this.events.destroy(this.dataEvents.dos);
  }

  cargarDireccionesLista(fromLogin: boolean = false) {
    this.genericService.sendGetRequest(environment.direcciones).subscribe(
      (response: any) => {
        //quitar
        this.listaDirecciones = response;
        this.render = true;
        if (this.listaDirecciones.length <= 0) {
          if (!fromLogin) {
            this.alertaService.warn('Aún no cuentas con direcciones frecuentes');
          }
        }
      },
      (error: HttpErrorResponse) => {
        let err: any = error.error;
        this.listaDirecciones = [];
        this.render = true;
        //this.alertaService.errorAlertGeneric(err.message ? err.message : "Ocurrió un error en el servicio, intenta nuevamente");
      }
    );
  }

  borrar(direccion: any) {
    let position: any = this.listaDirecciones.findIndex(img => {
      return img.id == direccion.id;
    });

    this.loadingService.show();
    this.genericService.sendDelete(`${environment.direcciones}/${direccion.id}`).subscribe(
      (response: any) => {
        this.listaDirecciones = [...this.listaDirecciones.slice(0, position), ...this.listaDirecciones.slice(position + 1)];
        this.loadingService.hide();
      },
      (error: HttpErrorResponse) => {
        this.loadingService.hide();
        this.alertaService.errorAlertGeneric('No se ha podido eliminar tu dirección, intenta nuevamente');
      }
    );
  }

  view(direccion: any) {
    this.navCtrl.push('main/proveedores-geo', { direccion: direccion });
  }

  select(direccion: any) {
    //this.viewCtrl.dismiss({ direccion });
  }

  nuevaLista() {
    this.localStorageEncryptService.clearProperty('direccion');
    this.navCtrl.push('main/proveedores-geo');
  }
}
