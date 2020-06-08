import { JhiEventManager } from 'ng-jhipster';
import { HistorialPedidosDetailPage } from './../historial-pedidos-detail/historial-pedidos-detail';
import { GenericService } from './../../services/generic.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { IonicPage, NavController, NavParams, PopoverController, Events } from 'ionic-angular';
import { environment } from '../../../environments/environment.prod';
import { User } from '../../models/User';
import { HttpErrorResponse } from '@angular/common/http';
import * as moment from 'moment';
import { AlertService } from 'app/services/alert.service';
import { LoadingService } from 'app/services/loading-service';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
import { NavParamsService } from 'app/services/nav-params.service';

@Component({
  selector: 'page-historial-pedidos',
  templateUrl: 'historial-pedidos.html',
  styleUrls: ['./historial-pedidos.scss']
})
export class HistorialPedidosPage implements OnInit, OnDestroy {
  public user: User = null;
  public pedidos: any = [];
  public pedidosReplica: any = [];

  public env: any = environment;

  public botones: any = {
    boton1: false,
    boton2: false,
    boton3: false
  };

  public dataEvents: any = {
    uno: null
  };
  constructor(
    private genericService: GenericService,
    private localStorageEncryptService: LocalStorageEncryptService,
    private alertaService: AlertService,
    private loadingService: LoadingService,
    private events: JhiEventManager,
    private navParams: NavParamsService
  ) {
    this.user = this.localStorageEncryptService.getFromLocalStorage('userSession');
    this.cargarPedidos();
    this.dataEvents.uno = this.events.subscribe('cargarPedidos', data => {
      try {
        this.cargarPedidos();
      } catch (error) {}
    });
  }

  ngOnInit() {}

  ngOnDestroy() {
    this.events.destroy(this.dataEvents.uno);
  }

  cargarPedidos() {
    this.genericService.sendGetRequest(`${environment.pedidos}`).subscribe(
      (response: any) => {
        this.pedidos = response;
        if (this.pedidos.length <= 0) {
          this.pedidos = null;
          this.alertaService.warn('Aún no cuentas con historial de pedidos');
          this.navParams.push('main/public-home');
        }
        this.pedidosReplica = this.pedidos;
      },
      (error: HttpErrorResponse) => {
        let err: any = error.error;
        this.pedidos = null;
        this.alertaService.errorAlertGeneric(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
      }
    );
  }

  viewDetail(pedido: any) {
    console.log(this.user);

    if (this.user.tipo_usuario > 1) {
      this.loadingService.show();
      this.genericService.sendGetRequest(`${environment.pedidos}/${pedido.id}`).subscribe(
        (response: any) => {
          this.loadingService.hide();
          //this.navCtrl.push(HistorialPedidosDetailPage, { pedido: response });
          this.navParams.push('main/detalle-pedido', { pedido: response });
        },
        (error: HttpErrorResponse) => {
          let err: any = error.error;
          this.loadingService.hide();
          this.alertaService.errorAlertGeneric(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
        }
      );
    } else {
      this.navParams.push('main/detalle-pedido', { pedido: pedido });
    }
  }

  ordenPor(opc) {
    this.pedidos = this.pedidosReplica;
    //item.fecha = moment(fechaF, 'DD-MM-YYYY HH:mm:ss').format("D [de] MMMM [de] YYYY HH:mm:ss");
    console.log(opc);
    console.log(this.pedidos);

    switch (opc) {
      case 1:
        //fecha solicitud
        this.pedidos.sort((mayor, menor) => {
          let dateA: any = moment(mayor.fechaAlta, 'DD-MM-YYYY HH:mm:ss').toDate();
          let dateB: any = moment(menor.fechaAlta, 'DD-MM-YYYY HH:mm:ss').toDate();
          console.log(dateA);
          console.log(dateB);

          return dateB - dateA;
          //return Math.abs(moment(mayor.fechaAlta, 'DD-MM-YYYY HH:mm:ss').toDate().getTime() - moment(menor.fechaAlta, 'DD-MM-YYYY HH:mm:ss').toDate().getTime());
        });
        this.botones.boton1 = !this.botones.boton1;
        break;
      case 2:
        //fecha entrega
        this.pedidos.sort((mayor, menor) => {
          let dateA: any = moment(mayor.fechaEntrega, 'DD-MM-YYYY HH:mm:ss').toDate();
          let dateB: any = moment(menor.fechaEntrega, 'DD-MM-YYYY HH:mm:ss').toDate();
          return dateB - dateA;
          //return Math.abs(moment(mayor.fechaAlta, 'DD-MM-YYYY HH:mm:ss').toDate().getTime() - moment(menor.fechaAlta, 'DD-MM-YYYY HH:mm:ss').toDate().getTime());
        });
        this.botones.boton2 = !this.botones.boton2;
        break;
      case 3:
        //estatus
        this.pedidos.sort((mayor, menor) => {
          let a: any = mayor.estatus.nombre;
          let b: any = menor.estatus.nombre;
          console.log(a);
          console.log(b);
          if (a > b) {
            return -1;
          }
          if (b > a) {
            return 1;
          }
          return 0;
        });
        this.botones.boton3 = !this.botones.boton3;
        break;

      case 4:
        //fecha solicitud
        this.pedidos.sort((mayor, menor) => {
          let dateA: any = moment(mayor.fechaAlta, 'DD-MM-YYYY HH:mm:ss').toDate();
          let dateB: any = moment(menor.fechaAlta, 'DD-MM-YYYY HH:mm:ss').toDate();
          console.log(dateA);
          console.log(dateB);

          return dateA - dateB;
          //return Math.abs(moment(mayor.fechaAlta, 'DD-MM-YYYY HH:mm:ss').toDate().getTime() - moment(menor.fechaAlta, 'DD-MM-YYYY HH:mm:ss').toDate().getTime());
        });
        this.botones.boton1 = !this.botones.boton1;
        break;
      case 5:
        //fecha entrega
        this.pedidos.sort((mayor, menor) => {
          let dateA: any = moment(mayor.fechaEntrega, 'DD-MM-YYYY HH:mm:ss').toDate();
          let dateB: any = moment(menor.fechaEntrega, 'DD-MM-YYYY HH:mm:ss').toDate();
          return dateA - dateB;
          //return Math.abs(moment(mayor.fechaAlta, 'DD-MM-YYYY HH:mm:ss').toDate().getTime() - moment(menor.fechaAlta, 'DD-MM-YYYY HH:mm:ss').toDate().getTime());
        });
        this.botones.boton2 = !this.botones.boton2;
        break;
      case 6:
        //estatus
        this.pedidos.sort((mayor, menor) => {
          let a: any = mayor.estatus.nombre;
          let b: any = menor.estatus.nombre;
          console.log(a);
          console.log(b);
          if (a < b) {
            return -1;
          }
          if (b < a) {
            return 1;
          }
          return 0;
        });
        this.botones.boton3 = !this.botones.boton3;
        break;
    }

    console.log(this.pedidos);
  }
}
