import { JhiEventManager } from 'ng-jhipster';

import { GenericService } from './../../services/generic.service';
import { Component, OnDestroy, Input, OnInit } from '@angular/core';
import { IonicPage, NavController, NavParams, AlertController, Platform, Events, ViewController, PopoverController } from 'ionic-angular';

import swal, { SweetAlertOptions } from 'sweetalert2';
import leaflet from 'leaflet';
import { environment } from '../../../environments/environment.prod';
import { HttpErrorResponse, HttpParams } from '@angular/common/http';
import { LoadingService } from 'app/services/loading-service';
import { AlertService } from 'app/services/alert.service';
import { NgbModal, NgbModalOptions, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { DireccionesPage } from '../direcciones/direcciones';
import { NavParamsService } from 'app/services/nav-params.service';

declare var google;
@Component({
  selector: 'page-home-geo-proveedores',
  templateUrl: 'home-geo-proveedores.html',
  styleUrls: ['./home-geo-proveedores.scss']
})
export class HomeGeoProveedoresPage implements OnDestroy, OnInit {
  @Input() fromModal;
  public map: any;

  public marker: any;

  public mapa: any;

  public autocomplete: any;

  public emulado: boolean = environment.emulado;

  public componentForm: any = {
    street_number: 'short_name',
    route: 'long_name',
    locality: 'long_name',
    administrative_area_level_1: 'short_name',
    country: 'long_name',
    postal_code: 'short_name'
  };

  public data: any = {};

  public muestraMapa: boolean = false;

  public tipoDirecciones: any = [];

  public direccion: any = null;

  public edit: boolean = false;

  public fromRegister: any = null;

  public listaDirecciones: any = [];

  public objGeo: any = {};

  constructor(
    private genericService: GenericService,
    private loadingService: LoadingService,
    private alertaService: AlertService,
    private events: JhiEventManager,
    public modalController: NgbModal,
    private navParams: NavParamsService,
    private activeModal: NgbActiveModal
  ) {
    this.direccion = navParams.get('direccion');
    this.fromModal = navParams.get('fromModal');
    this.fromRegister = navParams.get('fromRegister');
    if (this.direccion) {
      console.log(this.direccion);

      this.edit = true;
      /*
              codigoPostal: "89670"
              direccion: "Ocampo 508, Zona Centro, Aldama, Tamaulipas, México"
              latitud: "22.9221196"
              longitud: "-98.0690771"
              */
      this.data.codigoPostal = this.direccion.direccion.codigoPostal;
      this.data.direccion = this.direccion.direccion.direccion;
      this.data.latitud = this.direccion.direccion.latitud;
      this.data.longitud = this.direccion.direccion.longitud;
    }

    this.cargarTipoDirecciones();

    if (this.fromModal && !this.fromRegister) {
      this.cargarDireccionesLista();
    }
  }

  /**Método para cerrar el modal, sin embargo
   * se envían de vuelta los filtros para manipularlos en la búsqueda
   */
  dismiss() {
    //this.viewCtrl.dismiss({});
    this.activeModal.close();
  }

  cargarDireccionesLista() {
    this.genericService.sendGetRequest(environment.direcciones).subscribe(
      (response: any) => {
        this.listaDirecciones = response;
      },
      (error: HttpErrorResponse) => {
        let err: any = error.error;
        this.listaDirecciones = [];
        //this.alertaService.errorAlertGeneric(err.message ? err.message : "Ocurrió un error en el servicio, intenta nuevamente");
      }
    );
  }

  selectFrecuente() {
    /* let popover = this.popoverCtrl.create(DireccionesPage, { fromPop: true }, { cssClass: "clase-Pop-direcciones" });
     */
    let ngbModalOptions: NgbModalOptions = {
      backdrop: 'static',
      keyboard: false,
      backdropClass: 'backdrop2'
    };
    let modal: any = this.modalController.open(DireccionesPage, ngbModalOptions);

    modal.componentInstance.fromPop = true;

    modal.result.then(
      result => {
        let data = result;
        console.log(result);

        if (data) {
          if (data != null) {
            /*
              codigoPostal: "89670"
              direccion: "Ocampo 508, Zona Centro, Aldama, Tamaulipas, México"
              latitud: "22.9221196"
              longitud: "-98.0690771"
              */

            console.log(data.direccion);

            this.data.codigoPostal = data.direccion.direccion.codigoPostal;
            this.data.direccion = data.direccion.direccion.direccion;
            this.data.latitud = data.direccion.direccion.latitud;
            this.data.longitud = data.direccion.direccion.longitud;
            this.data.id = data.direccion.direccion.id;
            this.activeModal.close({ data: this.data });
          }
        }
      },
      reason => {}
    );

    /* popover.present({
    });
    popover.onDidDismiss((data) => {
      if (data) {
        if (data != null) {
          /*
              codigoPostal: "89670"
              direccion: "Ocampo 508, Zona Centro, Aldama, Tamaulipas, México"
              latitud: "22.9221196"
              longitud: "-98.0690771"
              
          this.data.codigoPostal = data.direccion.direccion.codigoPostal;
          this.data.direccion = data.direccion.direccion.direccion;
          this.data.latitud = data.direccion.direccion.latitud;
          this.data.longitud = data.direccion.direccion.longitud;
          this.data.id = data.direccion.direccion.id;
          this.viewCtrl.dismiss({ data: this.data });
        }
      }
    }); */
  }

  cargarTipoDirecciones() {
    this.genericService.sendGetRequest(environment.tipoDirecciones).subscribe(
      (response: any) => {
        this.tipoDirecciones = response;
      },
      (error: HttpErrorResponse) => {}
    );
  }

  ngOnInit() {
    let claseTabs: any = document.getElementsByClassName('tabbar');
    if (claseTabs[0]) {
      claseTabs[0].style.display = 'none';
    }
    this.obtenerLocalizacion();
    let as: any = document.getElementById('autocomplete');

    /* as.addEventListener("focus", (e: Event) => {
        this.geolocate();
    }); */
    this.autocomplete = new google.maps.places.Autocomplete(as, { types: ['geocode'] });

    console.log(this.autocomplete);

    // Avoid paying for data that you don't need by restricting the set of
    // place fields that are returned to just the address components.
    this.autocomplete.setFields(['address_component', 'geometry', 'name']);

    // When the user selects an address from the drop-down, populate the
    // address fields in the form.
    let componente: any = this;
    this.autocomplete.addListener('place_changed', function() {
      componente.fillInAddress(componente);
    });
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

  ngOnDestroy() {
    let claseTabs: any = document.getElementsByClassName('tabbar');
    if (claseTabs[0]) {
      claseTabs[0].style.display = 'flex';
    }
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
    let latitude = position.coords.latitude;
    let longitude = position.coords.longitude;

    this.objGeo.latitude = latitude;
    this.objGeo.longitude = longitude;
    // create a new map by passing HTMLElement
    let mapEle: HTMLElement = document.getElementById('map_canvas');

    // create LatLng object
    if (this.edit) {
      latitude = Number(this.direccion.direccion.latitud);
      longitude = Number(this.direccion.direccion.longitud);
    }
    let myLatLng = { lat: latitude, lng: longitude };

    // create map
    this.map = new google.maps.Map(mapEle, {
      center: myLatLng,
      zoom: 15
    });

    this.muestraMapa = true;
    google.maps.event.addListenerOnce(this.map, 'idle', () => {
      let info: any = `<div>Ejemplo de window</div>`;

      let infowindow: any = new google.maps.InfoWindow({
        content: info
      });
      let component: any = this;

      component.marker = new google.maps.Marker({
        position: myLatLng, //{ lat: -0.179041, lng: -78.499211 },
        map: this.map,
        title: 'Hello World!',
        id: 'marcador-1',
        draggable: true,
        icon: environment.icons['casa'].icon
      });

      this.data.latitud = latitude;
      this.data.longitud = longitude;
      let params = new HttpParams();
      params = params.set('latlng', `${this.data.latitud},${this.data.longitud}`);
      params = params.set('key', environment.keyGoogle);

      this.genericService.sendGetParams(`${environment.geocodeGoogle}`, params).subscribe(
        (response: any) => {
          this.loadingService.hide();
          this.map.setCenter(this.marker.position);
          this.marker.setMap(this.map);

          let results: any = response.results;
          if (results) {
            /*
          codigoPostal: "89670"
          direccion: "Ocampo 508, Zona Centro, Aldama, Tamaulipas, México"
          latitud: "22.9221196"
          longitud: "-98.0690771"
          */
            this.data.direccion = results[0].formatted_address;
            this.data.codigoPostal = '';
          }
        },
        (error: HttpErrorResponse) => {
          this.loadingService.hide();
          this.marker.setPosition(myLatLng);

          this.map.setCenter(myLatLng);
          this.marker.setMap(this.map);

          this.alertaService.errorAlertGeneric('No se obtuvo información del marcador, intenta nuevamente');
        }
      );

      component.marker.addListener('click', () => {
        //infowindow.open(this.map, this.marker);
        //component.changeInfoCard();
      });

      google.maps.event.addListener(component.marker, 'dragend', function(evt) {
        component.data.latitud = evt.latLng.lat().toString();
        component.data.longitud = evt.latLng.lng().toString();

        component.loadingService.show();
        let params = new HttpParams();
        params = params.set('latlng', `${component.data.latitud},${component.data.longitud}`);
        params = params.set('key', environment.keyGoogle);

        component.genericService.sendGetParams(`${environment.geocodeGoogle}`, params).subscribe(
          (response: any) => {
            component.loadingService.hide();
            component.map.setCenter(component.marker.position);
            component.marker.setMap(component.map);

            let results: any = response.results;
            if (results) {
              /*
              codigoPostal: "89670"
              direccion: "Ocampo 508, Zona Centro, Aldama, Tamaulipas, México"
              latitud: "22.9221196"
              longitud: "-98.0690771"
              */
              component.data.direccion = results[0].formatted_address;
              component.data.codigoPostal = '';
            }
          },
          (error: HttpErrorResponse) => {
            component.loadingService.hide();
            component.marker.setPosition(myLatLng);

            component.map.setCenter(myLatLng);
            component.marker.setMap(component.map);

            component.alertaService.errorAlertGeneric('No se obtuvo información del marcador, intenta nuevamente');
          }
        );
      });

      //'<p>Marker dropped: Current Lat: ' + evt.latLng.lat().toFixed(3) + ' Current Lng: ' + evt.latLng.lng().toFixed(3) + '</p>';

      /* marker.addEventListener("click", (e: Event) => {
        
      }); */

      mapEle.classList.add('show-map');
    });
  }

  changeInfoCard() {}

  loadMapLeaflet() {
    this.mapa = leaflet.map(`map`).setView([40.7127837, -74.0059413], 18);

    //let contributions
    // set map tiles source
    //leaflet.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    leaflet
      .tileLayer('https://cartodb-basemaps-{s}.global.ssl.fastly.net/light_all/{z}/{x}/{y}.png', {
        attribution: 'Central de Abastos &copy; <a href="https://www.sharkit.com/">Shark IT</a>',
        maxZoom: 20,
        zoom: 14
      })
      .addTo(this.mapa);

    let contributions: any = document.getElementsByClassName('leaflet-control-attribution');
    contributions[0].removeChild(contributions[0].childNodes[0]);

    this.mapa
      .locate({
        setView: true,
        maxZoom: 20,
        zoom: 14
      })
      .on('locationfound', e => {
        // add marker to the map
        var greenIcon = leaflet.icon({
          iconUrl: 'assets/images/marker.png',
          //shadowUrl: 'assets/images/marker.png',

          iconSize: [38, 38], // size of the icon
          shadowSize: [50, 64], // size of the shadow
          iconAnchor: [22, 94], // point of the icon which will correspond to marker's location
          shadowAnchor: [4, 62], // the same for the shadow
          popupAnchor: [-3, -76] // point from which the popup should open relative to the iconAnchor
        });

        let marker = leaflet.marker([40.7127837, -74.0059413], { icon: greenIcon }).addTo(this.mapa);
        // add popup to the marker
        var infoWindowContent = `<div class="contenedor">
                                  <div class="img">
                                    <img src="assets/imgs/home/basket.png" alt="">
                                  </div>
                                  <div class="titulo">Titulo de proveedor</div>
                                </div>`;
        marker.bindPopup(infoWindowContent).openPopup();
      })
      .on('locationerror', err => {
        //this.alertaService.errorAlert(this.alertaService.mensajeError, this.translatePipe.instant("ENABLED-GEOLOCATION"), null);
      });
  }

  geolocate() {
    if (navigator.geolocation) {
      let component: any = this;
      console.log(component);

      navigator.geolocation.getCurrentPosition(function(position) {
        var geolocation = {
          lat: position.coords.latitude,
          lng: position.coords.longitude
        };

        var circle = new google.maps.Circle({ center: geolocation, radius: position.coords.accuracy });

        console.log('......................................');

        console.log(component);

        component.autocomplete.setBounds(circle.getBounds());
      });
    }
  }

  getData() {
    return this.data;
  }

  cleanData() {
    this.data = {};
  }

  backData() {
    this.activeModal.close({ data: this.data });
  }

  guardar(body: any) {
    if (!this.edit) {
      this.loadingService.show();
      this.genericService.sendPostRequest(environment.direcciones, body).subscribe(
        (response: any) => {
          this.loadingService.hide();
          //this.events.publish("direction", { body, create: true });
          this.events.broadcast({ name: 'direction', content: { body, create: true } });
          if (!this.fromModal) {
            this.alertaService.info('Dirección agregada con éxito');
            //this.navCtrl.pop();
            this.navParams.push('main/direccion-frecuente');
          } else {
            this.activeModal.close({ data: this.data });
          }
        },
        (error: HttpErrorResponse) => {
          this.loadingService.hide();
          this.alertaService.errorAlertGeneric('No se ha podido agregar tu dirección frecuente, intenta nuevamente');
        }
      );
    } else {
      this.loadingService.show();
      this.genericService.sendPutRequest(environment.direcciones, body).subscribe(
        (response: any) => {
          this.alertaService.info('Dirección modificada con éxito');
          this.loadingService.hide();
          //this.events.publish("direction", { body, create: false });
          this.events.broadcast({ name: 'direction', content: { body, create: false } });
          this.navParams.push('main/direccion-frecuente');
        },
        (error: HttpErrorResponse) => {
          this.loadingService.hide();
          this.alertaService.errorAlertGeneric('No se ha podido modificar tu dirección frecuente, intenta nuevamente');
        }
      );
    }
  }

  fillInAddress(componente: any) {
    // Get the place details from the autocomplete object.
    var place = componente.autocomplete.getPlace();

    var lat = place.geometry.location.lat(),
      lng = place.geometry.location.lng();
    componente.cleanData();
    componente.marker.position = place.geometry.location;

    var latlng = new google.maps.LatLng(lat, lng);
    componente.marker.setPosition(latlng);

    componente.map.setCenter(place.geometry.location);

    componente.marker.setMap(componente.map);

    componente.getData().latitud = lat ? lat.toString() : '';
    componente.getData().longitud = lng ? lng.toString() : '';

    let completa: any = document.getElementById('autocomplete');
    componente.getData().direccion = completa ? completa.value.toString() : '';

    for (var component in componente.componentForm) {
      let a: any = document.getElementById(component);
      if (a) {
        a.value = '';
      }
      let b: any = document.getElementById(component);
      if (b) {
        b.disabled = false;
      }
    }

    // Get each component of the address from the place details,
    // and then fill-in the corresponding field on the form.
    if (place) {
      for (var i = 0; i < place.address_components.length; i++) {
        var addressType = place.address_components[i].types[0];

        if (componente.componentForm[addressType]) {
          var val = place.address_components[i][componente.componentForm[addressType]];

          switch (addressType) {
            case 'postal_code':
              componente.getData().codigoPostal = val ? val.toString() : '';
              break;

            default:
              break;
          }
          let c: any = document.getElementById(addressType);
          if (c) {
            c.value = val;
          }
        }
      }
    }
  }

  addToList() {
    let select: any = ``;

    let mensaje: string = ``;

    if (this.edit) {
      let select: any = `<select class='clase-select' id='select-direccion' value='${this.direccion.tipodireccionId}'>`;
      this.tipoDirecciones.forEach(element => {
        select += `<option value="${element.id}">${element.nombre}</option>`;
      });
      select += '</select>';
      mensaje = `Ingresa un alias y selecciona el tipo de dirección </br> 
      <input class="clase-select" placeholder="Ingresa el nombre" id="input-name" value='${this.direccion.alias}'></input>
      ${select}
      `;
    } else {
      let select: any = `<select class='clase-select' id='select-direccion' >`;
      this.tipoDirecciones.forEach(element => {
        select += `<option value="${element.id}">${element.nombre}</option>`;
      });
      select += '</select>';
      mensaje = `Ingresa un alias y selecciona el tipo de dirección </br> 
      <input class="clase-select" placeholder="Ingresa el nombre" id="input-name"></input>
      ${select}
      `;
    }

    swal
      .fire({
        title: 'Mi dirección frecuente',
        html: mensaje,
        type: 'info',
        showCancelButton: true,
        //confirmButtonColor: '#3085d6',
        //cancelButtonColor: '#d33',
        cancelButtonText: 'Cancelar',
        confirmButtonText: !this.edit ? 'Agregar' : 'Actualizar'
      })
      .then(result => {
        if (result.value) {
          let input: any = document.getElementById('input-name');
          let selectDireccion: any = document.getElementById('select-direccion');

          if (input.value.length <= 0) {
            this.alertaService.warn('Por favor ingresa un nombre a tu dirección');
          } else {
            let body: any = {
              alias: input.value,
              direccion: {
                codigoPostal: '',
                direccion: '',
                latitud: '',
                longitud: ''
              },
              tipodireccionId: selectDireccion.value
            };

            if (this.edit) {
              body.direccionId = this.direccion.direccionId;
              body.id = this.direccion.id;
            }
            /*
          codigoPostal: "89670"
          direccion: "Ocampo 508, Zona Centro, Aldama, Tamaulipas, México"
          latitud: "22.9221196"
          longitud: "-98.0690771"
          */
            body.direccion.codigoPostal = this.data.codigoPostal ? this.data.codigoPostal : '';
            body.direccion.direccion = this.data.direccion ? this.data.direccion : '';
            body.direccion.latitud = this.data.latitud ? this.data.latitud : '';
            body.direccion.longitud = this.data.longitud ? this.data.longitud : '';

            this.guardar(body);
          }
        }
      });
  }
}
