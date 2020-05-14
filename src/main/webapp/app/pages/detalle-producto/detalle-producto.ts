import { JhiEventManager } from 'ng-jhipster';

import { User } from './../../models/User';
import { GenericService } from './../../services/generic.service';
import { environment } from './../../../environments/environment.prod';
import { Component, ViewChild, OnDestroy, OnInit } from '@angular/core';
import { IonicPage, NavController, NavParams, Slides, Events } from 'ionic-angular';
import * as PhotoSwipe from 'photoswipe';
import * as PhotoSwipeUI_Default from 'photoswipe/dist/photoswipe-ui-default';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
import { LoadingService } from 'app/services/loading-service';
import { AlertService } from 'app/services/alert.service';
import { NavParamsService } from 'app/services/nav-params.service';

@Component({
  selector: 'page-detalle-producto',
  templateUrl: 'detalle-producto.html',
  styleUrls: ['./detalle-producto.scss']
})
export class DetalleProductoPage implements OnDestroy, OnInit {
  public producto: any = null;

  public productosTemp: any = [];
  public gallery: any;

  public verDescripcion: boolean = true;

  public user: User = null;

  public color: any = '#3b64c0';

  public data: any = {};

  public dynamicDots: any = `dots${Math.floor(Math.random() + 999999)}`;
  public leftArrowsSlider: any = `left-arrw-${Math.floor(Math.random() + 999999)}`;
  public rightArrowsSlider: any = `right-arrw-${Math.floor(Math.random() + 999999)}`;

  public slideConfig: any = {
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: true,
    appendDots: `.${this.dynamicDots}`,
    dots: true,
    autoplaySpeed: 3000,
    pauseOnDotsHover: true,
    infinite: true,
    focusOnSelect: true,
    nextArrow: "<div class='nav-btn next-slide'></div>",
    prevArrow: "<div class='nav-btn prev-slide'></div>"
  };

  constructor(
    private genericService: GenericService,
    private localStorageEncryptService: LocalStorageEncryptService,
    private auth: AuthService,
    private loadingService: LoadingService,
    private alertaService: AlertService,
    private eventManager: JhiEventManager,
    private navParams: NavParamsService
  ) {
    this.producto = navParams.get('producto');
    console.log(this.producto);

    this.user = this.localStorageEncryptService.getFromLocalStorage('userSession');

    this.producto.photos = [];
    if (this.producto.imagenes) {
      this.producto.imagenes.forEach(element => {
        this.producto.photos.push({
          id: element.id,
          img: `data:image/jpeg;base64,${element.file}`,
          view: false
        });
      });
    }

    if (!this.producto.cantidad) {
      this.producto.cantidad = 1;
      this.producto.first = true;
    }
    this.data.actualizarCantidad = this.eventManager.subscribe('actualizarCantidad', data => {
      try {
        this.actualizarCantidad();
      } catch (error) {}
    });

    this.data.reloadUser = this.eventManager.subscribe('reloadUser', data => {
      try {
        this.user = this.localStorageEncryptService.getFromLocalStorage('userSession');
      } catch (error) {}
    });

    if (this.localStorageEncryptService.getFromLocalStorage('theme')) {
      this.color = this.localStorageEncryptService.getFromLocalStorage('theme');
    }
    this.data.changeColor = this.eventManager.subscribe('changeColor', data => {
      try {
        if (this.localStorageEncryptService.getFromLocalStorage('theme')) {
          this.color = this.localStorageEncryptService.getFromLocalStorage('theme');
        }
      } catch (error) {}
    });
  }

  public slideIndex = 0;

  plusDivs(n, pos) {
    console.log('n' + n);
    switch (pos) {
      case 'left':
        this.slideIndex--;
        break;

      default:
        this.slideIndex++;
        break;
    }
    this.showDivs(this.slideIndex);
  }

  showDivs(i) {
    this.producto.photos.forEach(element => {
      element.view = false;
    });
    if (i < 0) {
      i = this.producto.photos.length - 1;
    } else if (i > this.producto.photos.length - 1) {
      i = 0;
    }
    console.log(i);
    this.slideIndex = i;
    this.producto.photos[i].view = true;
  }

  ngOnInit() {
    this.showDivs(0);

    this.producto.photos.forEach(phot => {
      let img: any = new Image();
      img.src = phot.img;
      img.onload = () => {
        this.productosTemp.push({ img: phot.img, w: img.width, h: img.height, i: phot.id });
      };
    });
  }

  ngOnDestroy() {
    try {
      this.eventManager.destroy(this.data.changeColor);
      this.eventManager.destroy(this.data.reloadUser);
      this.eventManager.destroy(this.data.actualizarCantidad);
    } catch (error) {}
  }

  actualizarCantidad() {
    let carritos = this.localStorageEncryptService.getFromLocalStorage(`${this.user.id_token}`);

    let position: any = carritos.findIndex(carrito => {
      return carrito.id == this.producto.id;
    });

    if (position >= 0) {
      this.producto.cantidad = carritos[position].cantidad;
    }
  }

  /**Métodos de navegacion del slide */
  next1() {
    //this.slider2.slideNext();
  }

  prev1() {
    //this.slider2.slidePrev();
  }

  ionViewDidLoad() {
    this.producto.photos.forEach(phot => {
      let img: any = new Image();
      img.src = phot.img;
      img.onload = () => {
        this.productosTemp.push({ img: phot.img, w: img.width, h: img.height, i: phot.id });
      };
    });
  }

  regresar() {
    let id: any = document.getElementById('icn-p');
    if (id) {
      id.style.display = 'none';
    }
    //this.navCtrl.pop();
  }

  imagesLoaded(i: number): void {
    let pswpElement: any = document.querySelectorAll('.pswp')[0];
    this.productosTemp.sort((a, b) => (a.i > b.i ? 1 : -1));

    // build items array
    let items: any[] = [];

    this.productosTemp.forEach(photo => {
      items.push({ src: photo.img, w: photo.w, h: photo.h });
    });

    // define options (if needed)
    let options = {
      // optionName: 'option value'
      // for example:
      index: i // start at first slide
    };

    // Initializes and opens PhotoSwipe
    this.gallery = new PhotoSwipe(pswpElement, PhotoSwipeUI_Default, items, options);
    this.gallery.init();
  }

  verDes() {
    this.verDescripcion = !this.verDescripcion;
  }

  incrementa(p: any) {
    let bandera: boolean = false;
    if (p.cantidad) {
      p.cantidad++;
    } else if (p.cantidad == 0) {
      p.cantidad = 1;
      bandera = true;
    } else {
      p.cantidad = 1;
      bandera = true;
    }
    //this.agregarToCarritoBack(bandera, p);
  }

  agregarToCarritoBack(bandera: boolean, producto: any) {
    let body: any = {
      precio: producto.precio,
      productoId: producto.id
    };
    let service: any = this.genericService.sendPostRequest(environment.carritoCompras, body);

    if (producto.cantidad > 1) {
      body.cantidad = producto.cantidad;
      service = this.genericService.sendPutRequest(environment.carritoCompras, body);
    }

    service.subscribe(
      (response: any) => {
        if (bandera) {
          //this.agregarToCarrito(producto);
        }
        //this.verificarCarritoModificarCantidad(producto);
      },
      (error: HttpErrorResponse) => {
        //producto.cantidad--;
        if (producto.cantidad == 1) {
          producto.cantidad = 1;
        } else {
          producto.cantidad--;
        }
      }
    );
  }

  decrementar(p: any) {
    if (p.cantidad > 1) {
      p.cantidad--;
      this.borrarToCarritoBack(p);
    }
  }

  borrarToCarritoBack(producto: any) {
    let body: any = {
      precio: producto.precio,
      productoProveedorId: producto.id
    };
    body.cantidad = producto.cantidad;
    this.genericService.sendPutRequest(environment.carritoCompras, body).subscribe(
      (response1: any) => {
        if (producto.cantidad == 0) {
          this.genericService.sendDeleteRequest(`${environment.carritoCompras}/${producto.id}`).subscribe(
            (response2: any) => {
              if (producto.cantidad == 0) {
                this.producto = 1;
                //this.productosCarrito = this.localStorageEncryptService.getFromLocalStorage(`${this.user.id_token}`);
              }
              this.verificarCarritoModificarCantidad(producto);
            },
            (error: HttpErrorResponse) => {
              producto.cantidad++;
            }
          );
        } else {
          this.verificarCarritoModificarCantidad(producto);
        }
      },
      (error: HttpErrorResponse) => {
        producto.cantidad++;
      }
    );
  }

  agregarCarrito(producto: any) {
    this.loadingService.show();
    //this.user = this.localStorageEncryptService.getFromLocalStorage("userSession");

    if (this.user) {
      let carritos = this.localStorageEncryptService.getFromLocalStorage(`${this.user.id_token}`);
      let body: any = {
        precio: producto.precio,
        productoProveedorId: producto.id
      };
      let b: boolean = false;
      let position: any;
      if (!carritos) {
        position = -1;
      } else {
        position = carritos.findIndex(carrito => {
          return carrito.id == producto.id;
        });
      }
      body.cantidad = producto.cantidad;
      if (position >= 0) {
        this.updateCarrito(producto, body);
      } else {
        this.genericService.sendPostRequest(environment.carritoCompras, body).subscribe(
          (response: any) => {
            body.cantidad = producto.cantidad;
            //this.alertaService.successAlertGeneric("Tu articulo se agregó al carrito con éxito");
            this.alertaService.success('Tu articulo se agregó al carrito con éxito');
            this.updateCarrito(producto, body);
            //this.verificarCarritoModificarCantidad(producto);
          },
          (error: HttpErrorResponse) => {
            this.alertaService.errorAlertGeneric(error.error.title);
            if (producto.cantidad == 1) {
              producto.cantidad = 1;
            } else {
              producto.cantidad--;
            }
            this.loadingService.hide();
          }
        );
      }
    } else {
      this.eventManager.broadcast({ name: 'startSession', content: {} });
    }
  }

  updateCarrito(producto: any, body: any) {
    this.genericService.sendPutRequest(environment.carritoCompras, body).subscribe(
      (response: any) => {
        this.loadingService.hide();
        this.verificarCarritoModificarCantidad(producto);
      },
      (error: HttpErrorResponse) => {
        this.loadingService.hide();
        if (producto.cantidad == 1) {
          producto.cantidad = 1;
        } else {
          producto.cantidad--;
        }
      }
    );
  }

  verificarCarritoModificarCantidad(element: any) {
    let productosStorage: any = this.localStorageEncryptService.getFromLocalStorage(`${this.user.id_token}`);
    if (!productosStorage) {
      productosStorage = [];
      productosStorage.push(element);
    } else {
      let position: any = productosStorage.findIndex(carrito => {
        return carrito.id == element.id;
      });

      if (position >= 0) {
        productosStorage[position].cantidad = element.cantidad;
      } else {
        productosStorage.push(element);
      }
    }
    this.localStorageEncryptService.setToLocalStorage(`${this.user.id_token}`, productosStorage);
    this.eventManager.broadcast('totalCarrito');
  }
}
