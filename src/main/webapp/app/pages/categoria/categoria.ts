import { AlertService } from './../../services/alert.service';

import { HttpErrorResponse } from '@angular/common/http';
import { GenericService } from './../../services/generic.service';
import { Component, OnDestroy } from '@angular/core';
import { environment } from '../../../environments/environment.prod';
import { User } from '../../models/User';
import { LoadingService } from 'app/services/loading-service';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
import { JhiEventManager } from 'ng-jhipster';
import { NavParamsService } from 'app/services/nav-params.service';
import { MapaProveedoresPage } from '../mapa-proveedores/mapa-proveedores';

@Component({
  selector: 'page-categoria',
  templateUrl: 'categoria.html',
  styleUrls: ['./categoria.scss']
})
export class CategoriaPage implements OnDestroy {
  public categoria: any = null;
  public articulos: any = null;

  public articulosReplica: any = null;

  public env: any = environment;

  public user: User = null;

  public color: any = '#3b64c0';

  public dataEvents: any = {};
  constructor(
    public navParams: NavParamsService,
    private genericService: GenericService,
    //private productoService: ProductoService,
    private loadingService: LoadingService,
    private alertaService: AlertService,
    private localStorageEncryptService: LocalStorageEncryptService,
    private eventManager: JhiEventManager
  ) {
    this.categoria = navParams.get('categoria');
    this.user = this.localStorageEncryptService.getFromLocalStorage('userSession');
    this.cargarArticulos();

    this.dataEvents.reloadUser = this.eventManager.subscribe('reloadUser', data => {
      try {
        this.user = this.localStorageEncryptService.getFromLocalStorage('userSession');
      } catch (error) {}
    });

    if (this.localStorageEncryptService.getFromLocalStorage('theme')) {
      this.color = this.localStorageEncryptService.getFromLocalStorage('theme');
    }
    this.dataEvents.changeColor = this.eventManager.subscribe('changeColor', data => {
      try {
        if (this.localStorageEncryptService.getFromLocalStorage('theme')) {
          this.color = this.localStorageEncryptService.getFromLocalStorage('theme');
        }
      } catch (error) {}
    });
  }

  ngOnDestroy() {
    try {
      this.eventManager.destroy(this.dataEvents.reloadUser);
      this.eventManager.destroy(this.dataEvents.changeColor);
    } catch (error) {}
  }

  viewDetail(producto: any) {
    //consumir servicio de imagenes completas
    this.loadingService.show();
    //this.user.parametros.pantalla_proveedores = "N";
    if (this.user && this.user.parametros.pantalla_proveedores == 'S') {
      this.genericService.sendGetRequest(`${environment.proveedorProductos}/producto/${producto.id}`).subscribe(
        (response: any) => {
          //this.navCtrl.push(MapaProveedoresPage, { proveedores: response, producto });
          this.navParams.push('/main/mapa-proveedores', { proveedores: response, producto });
          this.loadingService.hide();
        },
        (error: HttpErrorResponse) => {
          let err: any = error.error;
          this.alertaService.errorAlertGeneric(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
        }
      );
    } else {
      this.genericService.sendGetRequest(`${environment.proveedorProductos}/${producto.id}`).subscribe(
        (response: any) => {
          //ERROR SERVICIO NO ACTUALIZA CANTIDAD EN CARRITO
          //let nav = this.app.getRootNav();
          //let user: any = this.localStorageEncryptService.getFromLocalStorage("userSession");
          if (this.user) {
            let carritos = this.localStorageEncryptService.getFromLocalStorage(`${this.user.id_token}`);

            if (carritos) {
              let position: any = carritos.findIndex(carrito => {
                return carrito.id == response.id;
              });

              if (position >= 0) {
                response.cantidad = carritos[position].cantidad;
              }
            }
          }
          //this.navCtrl.push(DetalleProductoPage, { producto: response });
          this.navParams.push('main/detalle', { producto: response });
          this.loadingService.hide();
        },
        (error: HttpErrorResponse) => {
          this.loadingService.hide();
          let err: any = error.error;
          this.alertaService.errorAlertGeneric(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
        }
      );
    }
    //
  }

  verTodos(articulo: any) {
    //this.navCtrl.push(ArticuloProductosPage, { articulo });
  }

  cargarArticulos() {
    this.genericService.sendGetRequest(`${environment.categoria}${this.categoria.categoria.id}`).subscribe(
      (res: any) => {
        this.articulos = res.productosTipoArticulo;
        this.articulosReplica = this.articulos;
      },
      (err: HttpErrorResponse) => {}
    );
  }

  up() {
    this.articulos = this.articulosReplica;
    this.articulos.forEach(item1 => {
      item1.productos.sort((mayor, menor) => {
        return mayor.precio - menor.precio;
      });
    });
  }

  down() {
    this.articulos = this.articulosReplica;
    this.articulos.forEach(item1 => {
      item1.productos.sort((mayor, menor) => {
        return menor.precio - mayor.precio;
      });
    });
  }
}
