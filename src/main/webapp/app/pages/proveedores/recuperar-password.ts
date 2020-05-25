import { GenericService } from './../../services/generic.service';
import { Component } from '@angular/core';
import { environment } from '../../../environments/environment.prod';
import { HttpErrorResponse } from '@angular/common/http';
import { ArticuloProveedoresPage } from '../articulo-proveedores/articulo-proveedores';
import { NavParamsService } from 'app/services/nav-params.service';
import { AlertService } from 'app/services/alert.service';
import { LoadingService } from 'app/services/loading-service';

@Component({
  selector: 'page-recuperar-password',
  templateUrl: 'recuperar-password.html',
  styleUrls: ['./recuperar-password.scss']
})
export class ProveedorPage {
  public proveedores: any[] = [];
  public proveedoresReplica: any[] = [];
  public palabra: string = '';

  public env: any = environment;
  constructor(
    public navCtrl: NavParamsService,
    private genericService: GenericService,
    private alertaService: AlertService,
    private loadingService: LoadingService
  ) {
    this.cargarProveedores();
  }

  cargarProveedores() {
    this.genericService.sendGetRequest(`${environment.proveedoresFull}`).subscribe(
      (response: any) => {
        this.proveedores = response;
        this.proveedoresReplica = response;
      },
      (error: HttpErrorResponse) => {
        let err: any = error.error;
        this.proveedores = null;
        this.alertaService.errorAlertGeneric(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
      }
    );
  }

  ionViewDidLoad() {}

  regresar() {
    let id: any = document.getElementById('icn-2');
    id.style.display = 'none';
    this.navCtrl.push('main/public-home');
  }

  cambio() {}

  viewDetailAll(proveedor: any) {
    console.log(proveedor);

    //consumir servicio de imagenes completas
    this.loadingService.show();
    this.genericService.sendGetRequest(`${environment.proveedorProductos}/proveedor/${proveedor.id}`).subscribe(
      (response: any) => {
        this.loadingService.hide();
        this.navCtrl.push('main/articulo-proveedor', { productos: response, proveedor });
      },
      (error: HttpErrorResponse) => {
        this.loadingService.hide();
        let err: any = error.error;
        this.alertaService.errorAlertGeneric(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
      }
    );
    //
  }

  buscarPorPalabra() {
    this.proveedores = this.proveedoresReplica;
    this.proveedores = this.proveedores.filter((item: any) => item.nombre.toUpperCase().includes(this.palabra.toUpperCase()));
  }
}
