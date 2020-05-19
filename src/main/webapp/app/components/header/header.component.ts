import { AlertService } from './../../services/alert.service';
import { environment } from './../../../environments/environment.prod';
import { LocalStorageEncryptService } from './../../services/local-storage-encrypt-service';
import { JhiEventManager } from 'ng-jhipster';
import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { GenericService } from 'app/services/generic.service';
import { HttpErrorResponse } from '@angular/common/http';
import { NavParamsService } from 'app/services/nav-params.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, OnDestroy {
  public totalCarrito: any = 0;

  public user: any = null;

  public miEvents: any = {};
  constructor(
    private genericService: GenericService,
    private events: JhiEventManager,
    private localStorageEncryptService: LocalStorageEncryptService,
    private navParams: NavParamsService,
    private alertaService: AlertService
  ) {
    this.user = this.localStorageEncryptService.getFromLocalStorage('userSession');
  }

  ngOnInit() {
    this.miEvents.uno = this.events.subscribe('totalCarrito', data => {
      try {
        if (data) {
          this.totalCarrito = this.getTotalCarrito(data.fromLogin);
        } else {
          this.totalCarrito = this.getTotalCarrito();
        }
      } catch (error) {}
    });

    this.miEvents.dos = this.events.subscribe('totalCarrito2', data => {
      try {
        if (data) {
          this.totalCarrito = this.getTotalCarrito(data.fromLogin);
        } else {
          this.totalCarrito = this.getTotalCarrito();
        }
      } catch (error) {
        console.log(error);
      }
    });

    this.miEvents.tres = this.events.subscribe('updateProductos', data => {
      this.getTotalCarrito();
    });

    if (this.user) {
      this.getTotalCarrito();
    }
  }

  ngOnDestroy() {
    this.events.destroy(this.miEvents.uno);
    this.events.destroy(this.miEvents.dos);
    this.events.destroy(this.miEvents.tres);
  }

  getTotalCarrito(fromLogin: boolean = false) {
    this.genericService.sendGetRequest(environment.carritoCompras).subscribe(
      (response: any) => {
        this.localStorageEncryptService.setToLocalStorage(`${this.user.id_token}`, response);
        this.totalCarrito = response.length;
        console.log(this.totalCarrito);
      },
      (error: HttpErrorResponse) => {}
    );
  }

  a() {
    //if (this.genericService.getTotalCarrito() > 0) {

    //nav.pop();
    this.cargarProductosCarrito();

    //}
  }

  cargarProductosCarrito() {
    this.genericService.sendGetRequest(environment.carritoCompras).subscribe(
      (response: any) => {
        this.localStorageEncryptService.setToLocalStorage(`${this.user.id_token}`, response);
        this.navParams.push('main/carrito-compras');
      },
      (error: HttpErrorResponse) => {
        this.alertaService.warn('Agrega artículos al carrito');
      }
    );
  }

  b() {
    console.log('b');
  }
}
