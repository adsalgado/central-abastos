import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PedidoDetalle } from 'app/shared/model/pedido-detalle.model';
import { PedidoDetalleService } from './pedido-detalle.service';
import { PedidoDetalleComponent } from './pedido-detalle.component';
import { PedidoDetalleDetailComponent } from './pedido-detalle-detail.component';
import { PedidoDetalleUpdateComponent } from './pedido-detalle-update.component';
import { PedidoDetalleDeletePopupComponent } from './pedido-detalle-delete-dialog.component';
import { IPedidoDetalle } from 'app/shared/model/pedido-detalle.model';

@Injectable()
export class PedidoDetalleResolve implements Resolve<IPedidoDetalle> {
  constructor(private service: PedidoDetalleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPedidoDetalle> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PedidoDetalle>) => response.ok),
        map((pedidoDetalle: HttpResponse<PedidoDetalle>) => pedidoDetalle.body)
      );
    }
    return of(new PedidoDetalle());
  }
}

export const pedidoDetalleRoute: Routes = [
  {
    path: '',
    component: PedidoDetalleComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.pedidoDetalle.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PedidoDetalleDetailComponent,
    resolve: {
      pedidoDetalle: PedidoDetalleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.pedidoDetalle.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PedidoDetalleUpdateComponent,
    resolve: {
      pedidoDetalle: PedidoDetalleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.pedidoDetalle.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PedidoDetalleUpdateComponent,
    resolve: {
      pedidoDetalle: PedidoDetalleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.pedidoDetalle.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pedidoDetallePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PedidoDetalleDeletePopupComponent,
    resolve: {
      pedidoDetalle: PedidoDetalleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.pedidoDetalle.home.title'
    },
    canActivate: [UserRouteAccessService]
    //outlet: 'popup'
  }
];
