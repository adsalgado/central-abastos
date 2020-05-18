import { User } from './../../models/User';
import { Component, OnInit } from '@angular/core';
import { GenericService } from '../../services/generic.service';
import { environment } from '../../../environments/environment.prod';
import { HttpErrorResponse, HttpParams } from '@angular/common/http';
declare var google;
import leaflet from 'leaflet';
import leafletKnn from 'leaflet-knn';
import { AuthService } from '../../services/auth.service';
import { LoadingService } from 'app/services/loading-service';
import { AlertService } from 'app/services/alert.service';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
import { NavParamsService } from 'app/services/nav-params.service';

@Component({
  selector: 'page-mapa-proveedores',
  templateUrl: 'mapa-proveedores.html',
  styleUrls: ['./mapa-proveedores.scss']
})
export class MapaProveedoresPage implements OnInit {
  public emulado: boolean = environment.emulado;
  public muestraMapa: boolean = false;
  public map: any;

  public id: any = null;

  public proveedoresTotal: any = [];

  public proveedoresGeolocate: any = [];

  //public proveedores: any = [];

  public producto: any = null;

  public geo: any = null;

  public proveedorActivo: any = null;

  public env: any = environment;

  public user: User = null;

  public objGeo: any = {};
  constructor(
    private genericService: GenericService,
    private loadingService: LoadingService,
    private alertaService: AlertService,
    private navCtrl: NavParamsService,
    private localStorageEncryptService: LocalStorageEncryptService
  ) {
    this.user = this.localStorageEncryptService.getFromLocalStorage('userSession');
    this.proveedoresTotal = this.navCtrl.get('proveedores');

    this.producto = this.navCtrl.get('producto');
    console.log(this.proveedoresTotal);

    this.geo = [];
    this.proveedoresTotal.forEach(proveedorT => {
      //this.proveedores.push(proveedor);
      this.geo.push({
        type: 'Feature',
        geometry: {
          type: 'Point',
          coordinates: [proveedorT.proveedor.direccion.latitud, proveedorT.proveedor.direccion.longitud]
        },
        properties: {
          proveedor: proveedorT
        }
      });
    });
  }

  ngOnInit() {
    this.obtenerLocalizacion();
  }

  ionViewDidLoad() {
    let claseTabs: any = document.getElementsByClassName('tabbar');
    if (claseTabs[0]) {
      claseTabs[0].style.display = 'none';
    }
    this.obtenerLocalizacion();
  }

  ionViewWillLeave() {
    let claseTabs: any = document.getElementsByClassName('tabbar');
    if (claseTabs[0]) {
      claseTabs[0].style.display = 'flex';
    }
  }

  /**Método que obtiene la geolocalización del usuario
   * se utiliza al hacer click en el boton de posicionamiento
   */
  obtenerLocalizacion() {
    //this.loadingService.show().then(() => {
    if ('geolocation' in window) {
      //navigator.geolocation.getCurrentPosition(...);
      this.alertaService.warn('Activa tu geolocalización en el navegador y recarga');
    } else {
      this.getPosition();
    }
    //});
  }

  getPosition(): any {
    var options = {
      enableHighAccuracy: true,
      timeout: 60000,
      maximumAge: 0
    };

    let component: any = this;
    navigator.geolocation.getCurrentPosition(
      function(pos) {
        var crd = pos.coords;

        console.log('Your current position is:');
        console.log('Latitude : ' + crd.latitude);
        console.log('Longitude: ' + crd.longitude);
        console.log('More or less ' + crd.accuracy + ' meters.');
        component.loadingService.show();
        component.loadMap(pos);
      },
      function(err) {
        console.warn('ERROR(' + err.code + '): ' + err.message);
        component.alertaService.warn('ERROR(' + err.code + '): ' + err.message);
      },
      options
    );
  }

  nacional() {
    this.map.setZoom(5);
  }

  local() {
    this.map.setZoom(15);
    this.map.setCenter(new google.maps.LatLng(this.objGeo.latitude, this.objGeo.longitude));
  }

  loadMap(position) {
    console.log('-------------------------');
    this.loadingService.hide();
    let latitude = position.coords.latitude;
    let longitude = position.coords.longitude;

    this.objGeo.latitude = latitude;
    this.objGeo.longitude = longitude;
    // create a new map by passing HTMLElement
    let mapEle: HTMLElement = document.getElementById('map_canvas');

    let myLatLng = { lat: latitude, lng: longitude };

    var gj = leaflet.geoJson(this.geo);
    var nearest = leafletKnn(gj).nearest([latitude, longitude], 50, 5000); //punto de partida, estaciones máximas a encontrar, diámetro de busqueda en metros

    // create map
    if (nearest.length > 0) {
      this.map = new google.maps.Map(mapEle, {
        center: myLatLng,
        zoom: 15
      });

      this.muestraMapa = true;
      google.maps.event.addListenerOnce(this.map, 'idle', () => {
        let primero: number = 0;
        nearest.forEach(item => {
          this.proveedoresGeolocate.push(item.layer.feature.properties.proveedor);
          let ll = { lat: Number(item.lon), lng: Number(item.lat) };

          let info: any = `
        <div>
          <div style="    text-align: center;
          font-weight: 700;
          font-size: 19px;
      ">${item.layer.feature.properties.proveedor.proveedor.nombre}</div>
          <div style="width:100%; text-align: center">
            <div ><strong>Dirección</strong></div>
            <div >${item.layer.feature.properties.proveedor.proveedor.direccion.direccion}</div>
          </div>
          <div style="width:100%; text-align: center">
            <div ><strong>Empresa</strong></div>
            <div >${item.layer.feature.properties.proveedor.proveedor.empresa.nombre}</div>
          </div>
        </div>`;

          let infowindow: any = new google.maps.InfoWindow({
            content: info
          });

          let marker = new google.maps.Marker({
            position: ll, //{ lat: -0.179041, lng: -78.499211 },
            map: this.map,
            title: item.layer.feature.properties.proveedor.proveedor.nombre,
            id: `${item.layer.feature.properties.proveedor.id}`,
            //draggable: true,
            icon: environment.icons['proveedor'].icon
          });

          //this.map.setCenter(marker.position);
          marker.setMap(this.map);

          let componente: any = this;
          marker.addListener('click', () => {
            infowindow.open(this.map, marker);
            componente.changeInfoCard(marker);
          });
          if (primero == 0) {
            new google.maps.event.trigger(marker, 'click');
          }
          primero++;
        });

        mapEle.classList.add('show-map');
      });
    } else {
      this.alertaService.warn('Lo sentimos, no hay proveedores cerca de tu ubicación');
      //this.navCtrl.pop();
    }
  }

  changeInfoCard(marker: any) {
    let position: any = this.proveedoresTotal.findIndex(carrito => {
      return carrito.id == marker.id;
    });

    this.proveedorActivo = this.proveedoresTotal[position];
  }

  comparativa() {
    //this.navCtrl.push(ComparaPreciosProveedorPage, { proveedoresGeolocate: this.proveedoresGeolocate });
  }

  viewDetailAll(proveedor: any) {
    //consumir servicio de imagenes completas
    this.loadingService.show();
    this.genericService.sendGetRequest(`${environment.proveedorProductos}/proveedor/${proveedor.proveedorId}`).subscribe(
      (response: any) => {
        this.loadingService.hide();
        //this.navCtrl.push(ArticuloProveedoresPage, { productos: response, proveedor });
      },
      (error: HttpErrorResponse) => {
        this.loadingService.hide();
        let err: any = error.error;
        this.alertaService.errorAlertGeneric(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
      }
    );
    //
  }

  agregarCarrito() {
    this.loadingService.show();
    //this.user = this.localStorageEncryptService.getFromLocalStorage("userSession");
    if (this.user) {
      let body: any = {
        precio: this.proveedorActivo.precio,
        productoProveedorId: this.proveedorActivo.id,
        cantidad: 1
      };
      this.genericService.sendPostRequest(environment.carritoCompras, body).subscribe(
        (response: any) => {
          body.cantidad = 1;
          this.loadingService.hide();
          this.alertaService.success('Tu articulo se agregó al carrito con éxito');
          //this.events.publish("totalCarrito2");
          //this.verificarCarritoModificarCantidad(producto);
        },
        (error: HttpErrorResponse) => {
          this.alertaService.errorAlertGeneric(error.error.title);
          this.loadingService.hide();
        }
      );
    } else {
      //this.auth.events.publish("startSession");
    }
  }
}
