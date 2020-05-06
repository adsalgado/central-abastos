import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CarritoCompra } from 'app/shared/model/carrito-compra.model';
import { CarritoCompraService } from './carrito-compra.service';
import { CarritoCompraComponent } from './carrito-compra.component';
import { CarritoCompraDetailComponent } from './carrito-compra-detail.component';
import { CarritoCompraUpdateComponent } from './carrito-compra-update.component';
import { CarritoCompraDeletePopupComponent } from './carrito-compra-delete-dialog.component';
import { ICarritoCompra } from 'app/shared/model/carrito-compra.model';

@Injectable()
export class CarritoCompraResolve implements Resolve<ICarritoCompra> {
  constructor(private service: CarritoCompraService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICarritoCompra> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CarritoCompra>) => response.ok),
        map((carritoCompra: HttpResponse<CarritoCompra>) => carritoCompra.body)
      );
    }
    return of(new CarritoCompra());
  }
}

export const carritoCompraRoute: Routes = [
  {
    path: '',
    component: CarritoCompraComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.carritoCompra.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CarritoCompraDetailComponent,
    resolve: {
      carritoCompra: CarritoCompraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.carritoCompra.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CarritoCompraUpdateComponent,
    resolve: {
      carritoCompra: CarritoCompraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.carritoCompra.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CarritoCompraUpdateComponent,
    resolve: {
      carritoCompra: CarritoCompraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.carritoCompra.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const carritoCompraPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CarritoCompraDeletePopupComponent,
    resolve: {
      carritoCompra: CarritoCompraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.carritoCompra.home.title'
    },
    canActivate: [UserRouteAccessService]
    ////outlet: 'popup'
  }
];
