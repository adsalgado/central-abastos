import { environment } from './../../../environments/environment.prod';
import { GenericService } from './../../services/generic.service';
import { Component } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { DetalleProductoPage } from '../detalle-producto/detalle-producto';
import { JhiEventManager } from 'ng-jhipster';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
import { AlertService } from 'app/services/alert.service';
import { LoadingService } from 'app/services/loading-service';
import { NavParamsService } from 'app/services/nav-params.service';

@Component({
  selector: 'page-compara-precios-proveedor',
  templateUrl: 'compara-precios-proveedor.html',
  styleUrls: ['./compara-precios-proveedor.scss']
})
export class ComparaPreciosProveedorPage {
  public proveedoresGeolocate: any = null;
  public proveedoresGeolocateReplica: any = null;
  public env: any = environment;

  public palabra: string = '';

  public multiple: boolean = false;
  constructor(
    private genericService: GenericService,
    private alertaService: AlertService,
    private loadingService: LoadingService,
    private localStorageEncryptService: LocalStorageEncryptService,
    private eventManager: JhiEventManager,
    private navParams: NavParamsService
  ) {
    this.proveedoresGeolocate = navParams.get('proveedoresGeolocate');
    this.multiple = navParams.get('multiple');
    this.proveedoresGeolocateReplica = this.proveedoresGeolocate;
    console.log(this.proveedoresGeolocate);
  }

  buscarPorPalabra() {
    this.proveedoresGeolocate = this.proveedoresGeolocateReplica;
    this.proveedoresGeolocate = this.proveedoresGeolocate.filter((item: any) =>
      item.proveedor.nombre.toUpperCase().includes(this.palabra.toUpperCase())
    );
  }

  viewDetail(producto: any) {
    //consumir servicio de imagenes completas
    this.loadingService.show();
    this.genericService.sendGetRequest(`${environment.proveedorProductos}/${producto.productoId}`).subscribe(
      (response: any) => {
        this.navParams.push('main/detalle', { producto: response });
        this.loadingService.hide();
      },
      (error: HttpErrorResponse) => {
        this.loadingService.hide();
        let err: any = error.error;
        this.alertaService.errorAlertGeneric(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
      }
    );
    //
  }

  up() {
    this.proveedoresGeolocate = this.proveedoresGeolocateReplica;
    this.proveedoresGeolocate.sort((mayor, menor) => {
      return mayor.precio - menor.precio;
    });
  }

  down() {
    this.proveedoresGeolocate = this.proveedoresGeolocateReplica;
    this.proveedoresGeolocate.sort((mayor, menor) => {
      return menor.precio - mayor.precio;
    });
  }
}
