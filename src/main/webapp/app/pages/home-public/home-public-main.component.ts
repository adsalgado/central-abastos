import { NavParamsService } from './../../services/nav-params.service';
import { AlertService } from './../../services/alert.service';
import { LoadingService } from './../../services/loading-service';
import { environment } from '../../../environments/environment.prod';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { User } from 'app/models/User';
import { Categoria } from 'app/models/Categoria';
import { Proveedor } from 'app/models/Proveedor';
import { Seccion } from 'app/models/Seccion';
import { HttpServiceGeneric } from 'app/services/http.generic.service';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
import { HttpErrorResponse, HttpParams } from '@angular/common/http';

@Component({
  selector: 'home-public-main',
  templateUrl: './home-public-main.component.html',
  styleUrls: ['./home-public-main.component.scss']
})
export class HomePublicMainComponent implements OnDestroy, OnInit {
  public productos: any = [];
  public promociones: any = [];
  public productosBuscados: any = [];
  public productosCategorias: any = [];
  public productosCategoriasSub: any = [];

  public categorias: Categoria[] = [];
  public proveedores: Proveedor[] = [];
  public secciones: Seccion[] = [];

  public imgBusqueda: any = '/../../../content/imgs/home/search.png';
  public textoBusqueda: string = '';

  public pruebaImg: string = '../../../content/imgs/home/images.jpeg';

  private dataFilter: any = {
    idProveedor: null,
    idSeccion: null,
    idCategoria: null,
    nombre: ''
  };

  private objCombos: any = {
    secciones: this.secciones,
    proveedores: this.proveedores,
    categorias: this.categorias
  };

  public env: any = environment;

  public user: User = null;

  public totalCarrito: any = 0;

  public color: any = '#3b64c0';
  constructor(
    private loadingService: LoadingService,
    private genericService: HttpServiceGeneric,
    private alertaService: AlertService,
    private localStorageEncryptService: LocalStorageEncryptService,
    private navParamsService: NavParamsService
  ) {
    this.user = this.localStorageEncryptService.getFromLocalStorage(`userSession`);
  }

  cargaPromociones() {
    this.genericService.sendGetRequest(environment.promociones).subscribe(
      (response: any) => {
        this.promociones = response;
        this.verificarCarrito();
      },
      (error: HttpErrorResponse) => {
        this.promociones = null;
      }
    );
  }

  getTotalCarrito() {
    this.genericService.sendGetRequest(environment.carritoCompras).subscribe(
      (response: any) => {
        console.log(response);
        this.localStorageEncryptService.setToLocalStorage(`${this.user.id_token}`, response);
        this.totalCarrito = response.length;
      },
      (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
  }

  ngOnDestroy() {
    //this.events.unsubscribe("updateProductos");
  }

  ngOnInit() {
    this.cargarProductosPorCategoria(1);
    this.cargaPromociones();
    //this.cargarProductos();

    //this.cargarProveedores();
    //this.cargarSecciones();
    //this.cargarCategorias();

    if (this.user) {
      this.getTotalCarrito();
    }

    //this.cargarProductosCarrito();
  }

  cargarProductosCarrito() {
    this.genericService.sendGetRequest(environment.carritoCompras).subscribe(
      (response: any) => {
        console.log(response);
        this.localStorageEncryptService.setToLocalStorage(`${this.user.id_token}`, response);
        //nav.push(CarritoComprasPage);
        this.navParamsService.push('carrito-compras');
      },
      (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
  }

  buscando() {
    if (this.textoBusqueda.length > 0) {
      this.imgBusqueda = '/../../../content/imgs/home/close.png';
    } else {
      this.imgBusqueda = '/../../../content/imgs/home/search.png';
    }
  }

  close() {
    if (this.imgBusqueda == '/../../../content/imgs/home/close.png') {
      this.imgBusqueda = '/../../../content/imgs/home/search.png';
      this.textoBusqueda = '';
    }
  }

  verificarCarrito() {
    if (this.user) {
      let productosStorage: any = this.localStorageEncryptService.getFromLocalStorage(`${this.user.id_token}`);
      console.log(productosStorage);

      if (productosStorage) {
        productosStorage.forEach(item => {
          this.productos.forEach(element => {
            if (item.id == element.id) {
              element.carrito = true;
              element.cantidad = item.cantidad;
            }
          });
        });
      }
    }
  }

  verificarCarritoModificarCantidad(element: any) {
    let productosStorage: any = this.localStorageEncryptService.getFromLocalStorage(`${this.user.id_token}`);
    if (productosStorage) {
      productosStorage.forEach(item => {
        if (item.id == element.id) {
          item.cantidad = element.cantidad;
        }
      });
    }
    this.localStorageEncryptService.setToLocalStorage(`${this.user.id_token}`, productosStorage);
  }

  cargarSecciones() {
    this.genericService.sendGetRequest(environment.secciones, Seccion).subscribe(
      (response: any) => {
        this.secciones = response;
        this.objCombos.secciones = this.secciones;
        //quitar
      },
      (error: HttpErrorResponse) => {
        let err: any = error.error;
        //this.alertaService.errorAlertGeneric(err.message ? err.message : "Ocurrió un error en el servicio, intenta nuevamente");
      }
    );
  }

  cargarProveedores() {
    this.genericService.sendGetRequest(environment.proveedores, Proveedor).subscribe(
      (response: any) => {
        this.proveedores = response;
        this.objCombos.proveedores = this.proveedores;

        //quitar
      },
      (error: HttpErrorResponse) => {
        let err: any = error.error;
        //this.alertaService.errorAlertGeneric(err.message ? err.message : "Ocurrió un error en el servicio, intenta nuevamente");
      }
    );
  }

  cargarCategorias() {
    this.genericService.sendGetRequest(environment.categorias, Categoria).subscribe(
      (response: any) => {
        this.categorias = response;
        this.objCombos.categorias = this.categorias;
        //quitar
      },
      (error: HttpErrorResponse) => {
        let err: any = error.error;
        //this.alertaService.errorAlertGeneric(err.message ? err.message : "Ocurrió un error en el servicio, intenta nuevamente");
      }
    );
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
    this.agregarToCarritoBack(bandera, p);
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
          this.agregarToCarrito(producto);
        }
        this.verificarCarritoModificarCantidad(producto);
      },
      (error: HttpErrorResponse) => {
        if (producto.cantidad == 1) {
          producto.cantidad = 1;
        } else {
          producto.cantidad--;
        }
      }
    );
  }

  /* decrementar(p: any) {
    p.cantidad--;
    this.borrarToCarritoBack(p);
  } */

  /* borrarToCarritoBack(producto: any) {
    let body: any = {
      precio: producto.precio,
      productoId: producto.id
    }
    body.cantidad = producto.cantidad;
    this.genericService.sendPutRequest(environment.carritoCompras, body).subscribe((response1: any) => {
      this.genericService.sendDeleteRequest(`${environment.carritoCompras}/${producto.id}`).subscribe((response2: any) => {
        if (producto.cantidad == 0) {
          this.productoService.deleteFavorito(producto);
          //this.productosCarrito = this.localStorageEncryptService.getFromLocalStorage(`${this.user.id_token}`);
        }
        this.verificarCarritoModificarCantidad(producto);
      }, (error: HttpErrorResponse) => {
        producto.cantidad++;
      });
    }, (error: HttpErrorResponse) => {
      producto.cantidad++;
    });
  } */
  /* borrarToCarritoBack(producto: any) {
    this.genericService.sendDeleteRequest(`${environment.carritoCompras}/${producto.id}`).subscribe((response: any) => {
      if (producto.cantidad == 0) {
        this.productoService.deleteFavorito(producto);
      }
      this.verificarCarritoModificarCantidad(producto);
    }, (error: HttpErrorResponse) => {
      producto.cantidad++;
    });
  } */

  /**Método para cargar productos en base a especificaciones */
  cargarProductos() {
    console.log('in method');

    this.loadingService.show();
    this.genericService.sendGetRequest(environment.productos).subscribe(
      (response: any) => {
        console.log(response);
        //quitar
        this.productos = response;
        console.log(this.productos);
        this.verificarCarrito();
        this.loadingService.hide();
      },
      (error: HttpErrorResponse) => {
        this.loadingService.hide();
        let err: any = error.error;
        this.alertaService.errorAlertGeneric(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
      }
    );
  }

  /**Método para cargar productos en base a especificaciones */
  cargarProductosPorCategoria(opc: number) {
    console.log('in method');
    this.productosCategorias = [];
    this.productosCategoriasSub = [];
    this.loadingService.show();
    this.genericService.sendGetRequest(`${environment.proveedorProductos}/home/${opc}`).subscribe(
      (response: any) => {
        console.log(response);
        //quitar
        this.productosCategorias = response.productosCategoria;
        for (let index = 1; index < this.productosCategorias.length; index++) {
          const element = this.productosCategorias[index];
          this.productosCategoriasSub.push(element);
        }
        console.log(this.productos);
        this.verificarCarrito();
        this.loadingService.hide();
      },
      (error: HttpErrorResponse) => {
        this.loadingService.hide();
        let err: any = error.error;
        this.alertaService.errorAlertGeneric(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
      }
    );
  }

  ordena(opc) {
    this.cargarProductosPorCategoria(opc);
  }

  buscarPorFiltros() {
    let params = new HttpParams();

    if (this.dataFilter.idProveedor) {
      params = params.set('proveedorId', this.dataFilter.idProveedor ? this.dataFilter.idProveedor : '');
    }
    if (this.dataFilter.idSeccion) {
      params = params.set('seccionId', this.dataFilter.idSeccion ? this.dataFilter.idSeccion : '');
    }
    if (this.dataFilter.idCategoria) {
      params = params.set('categoriaId', this.dataFilter.idCategoria ? this.dataFilter.idCategoria : '');
    }
    if (this.dataFilter.nombre) {
      console.log('---');

      params = params.append('nombre', this.dataFilter.nombre);
    }
    console.log(params);

    this.productosBuscados = [];
    this.genericService.sendGetParams(`${environment.proveedorProductos}/search`, params).subscribe(
      (response: any) => {
        console.log(response);
        this.productosBuscados = response;
        this.loadingService.hide();
      },
      (error: HttpErrorResponse) => {
        this.loadingService.hide();
        let err: any = error.error;
        this.alertaService.errorAlertGeneric(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
      }
    );
  }

  agregarToCarrito(producto: any) {
    let productosStorage: any = this.localStorageEncryptService.getFromLocalStorage(`${this.user.id_token}`);
    let productos: any = [];
    productos.push(producto);
    if (productosStorage) {
      productosStorage.forEach(element => {
        productos.push(element);
      });
    }
    producto.carrito = true;
    try {
      this.localStorageEncryptService.setToLocalStorage(`${this.user.id_token}`, productos);
    } catch (error) {
      producto.carrito = false;
    }
  }

  verCarrito() {
    if (this.genericService.getTotalCarrito() > 0) {
      //nav.pop();
      this.cargarProductosCarrito();
    }
  }

  irToCategoria(categoria: any) {
    //this.navCtrl.push(CategoriaPage, { categoria });
    this.navParamsService.push('main/categoria', { categoria: categoria });
  }

  viewDetail(producto: any) {
    //consumir servicio de imagenes completas
    this.loadingService.show();
    this.genericService.sendGetRequest(`${environment.proveedorProductos}/${producto.id}`).subscribe(
      (response: any) => {
        console.log(response);

        //ERROR SERVICIO NO ACTUALIZA CANTIDAD EN CARRITO
        //let nav = this.app.getRootNav();
        //let user: any = this.localStorageEncryptService.getFromLocalStorage("userSession");
        if (this.user) {
          let carritos = this.localStorageEncryptService.getFromLocalStorage(`${this.user.id_token}`);
          console.log(carritos);

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

        this.navParamsService.push('detalle-producto', { producto: response });
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
}
