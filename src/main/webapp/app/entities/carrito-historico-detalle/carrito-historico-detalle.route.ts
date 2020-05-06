import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CarritoHistoricoDetalle } from 'app/shared/model/carrito-historico-detalle.model';
import { CarritoHistoricoDetalleService } from './carrito-historico-detalle.service';
import { CarritoHistoricoDetalleComponent } from './carrito-historico-detalle.component';
import { CarritoHistoricoDetalleDetailComponent } from './carrito-historico-detalle-detail.component';
import { CarritoHistoricoDetalleUpdateComponent } from './carrito-historico-detalle-update.component';
import { CarritoHistoricoDetalleDeletePopupComponent } from './carrito-historico-detalle-delete-dialog.component';
import { ICarritoHistoricoDetalle } from 'app/shared/model/carrito-historico-detalle.model';

@Injectable()
export class CarritoHistoricoDetalleResolve implements Resolve<ICarritoHistoricoDetalle> {
  constructor(private service: CarritoHistoricoDetalleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICarritoHistoricoDetalle> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CarritoHistoricoDetalle>) => response.ok),
        map((carritoHistoricoDetalle: HttpResponse<CarritoHistoricoDetalle>) => carritoHistoricoDetalle.body)
      );
    }
    return of(new CarritoHistoricoDetalle());
  }
}

export const carritoHistoricoDetalleRoute: Routes = [
  {
    path: '',
    component: CarritoHistoricoDetalleComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.carritoHistoricoDetalle.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CarritoHistoricoDetalleDetailComponent,
    resolve: {
      carritoHistoricoDetalle: CarritoHistoricoDetalleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.carritoHistoricoDetalle.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CarritoHistoricoDetalleUpdateComponent,
    resolve: {
      carritoHistoricoDetalle: CarritoHistoricoDetalleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.carritoHistoricoDetalle.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CarritoHistoricoDetalleUpdateComponent,
    resolve: {
      carritoHistoricoDetalle: CarritoHistoricoDetalleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.carritoHistoricoDetalle.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const carritoHistoricoDetallePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CarritoHistoricoDetalleDeletePopupComponent,
    resolve: {
      carritoHistoricoDetalle: CarritoHistoricoDetalleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.carritoHistoricoDetalle.home.title'
    },
    canActivate: [UserRouteAccessService]
    ////outlet: 'popup'
  }
];
