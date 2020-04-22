import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HistoricoPedido } from 'app/shared/model/historico-pedido.model';
import { HistoricoPedidoService } from './historico-pedido.service';
import { HistoricoPedidoComponent } from './historico-pedido.component';
import { HistoricoPedidoDetailComponent } from './historico-pedido-detail.component';
import { HistoricoPedidoUpdateComponent } from './historico-pedido-update.component';
import { HistoricoPedidoDeletePopupComponent } from './historico-pedido-delete-dialog.component';
import { IHistoricoPedido } from 'app/shared/model/historico-pedido.model';

@Injectable({ providedIn: 'root' })
export class HistoricoPedidoResolve implements Resolve<IHistoricoPedido> {
  constructor(private service: HistoricoPedidoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHistoricoPedido> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<HistoricoPedido>) => response.ok),
        map((historicoPedido: HttpResponse<HistoricoPedido>) => historicoPedido.body)
      );
    }
    return of(new HistoricoPedido());
  }
}

export const historicoPedidoRoute: Routes = [
  {
    path: '',
    component: HistoricoPedidoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.historicoPedido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HistoricoPedidoDetailComponent,
    resolve: {
      historicoPedido: HistoricoPedidoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.historicoPedido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HistoricoPedidoUpdateComponent,
    resolve: {
      historicoPedido: HistoricoPedidoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.historicoPedido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HistoricoPedidoUpdateComponent,
    resolve: {
      historicoPedido: HistoricoPedidoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.historicoPedido.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const historicoPedidoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: HistoricoPedidoDeletePopupComponent,
    resolve: {
      historicoPedido: HistoricoPedidoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.historicoPedido.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
