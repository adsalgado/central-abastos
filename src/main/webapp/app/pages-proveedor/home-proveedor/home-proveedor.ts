import { GenericService } from './../../services/generic.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { User } from '../../models/User';
import { environment } from '../../../environments/environment.prod';
import { HttpErrorResponse } from '@angular/common/http';
import * as moment from 'moment';
import { HistorialPedidosDetailPage } from '../../pages/historial-pedidos-detail/historial-pedidos-detail';
import { NavParamsService } from 'app/services/nav-params.service';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
import { AlertService } from 'app/services/alert.service';
import { LoadingService } from 'app/services/loading-service';
import { JhiEventManager } from 'ng-jhipster';

@Component({
  selector: 'page-home-proveedor',
  templateUrl: 'home-proveedor.html',
  styleUrls: ['./home-proveedor.scss']
})
export class HomeProveedorPage implements OnInit, OnDestroy {
  public user: User = null;
  public pedidos: any = [];
  public pedidosReplica: any = [];

  public botones: any = {
    boton1: false,
    boton2: false,
    boton3: false
  };

  public dataEvents: any = {
    uno: null
  };
  constructor(
    public navCtrl: NavParamsService,
    private localStorageEncryptService: LocalStorageEncryptService,
    private genericService: GenericService,
    private alertaService: AlertService,
    private loadingService: LoadingService,
    private events: JhiEventManager
  ) {
    /**Obtenci{on de usuario en sesión */
    this.user = this.localStorageEncryptService.getFromLocalStorage(`userSession`);

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
    let path: string = `${environment.pedidosProveedor}`;
    let user: any = this.localStorageEncryptService.getFromLocalStorage('userSession');
    if (user.tipo_usuario == 4) {
      path = `${environment.pedidosTransportista}`;
    }

    this.genericService.sendGetRequest(path).subscribe(
      (response: any) => {
        this.pedidos = response;
        if (this.pedidos.length <= 0) {
          this.pedidos = null;
          this.alertaService.warn('Aún no cuentas con historial de pedidos');
          this.navCtrl.push('main/public-home');
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
    //this.navCtrl.push(HistorialPedidosDetailPage, { pedido });
    this.navCtrl.push('main/detalle-pedido', { pedido: pedido });
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
