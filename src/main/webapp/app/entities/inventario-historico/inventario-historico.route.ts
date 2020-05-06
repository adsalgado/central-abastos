import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InventarioHistorico } from 'app/shared/model/inventario-historico.model';
import { InventarioHistoricoService } from './inventario-historico.service';
import { InventarioHistoricoComponent } from './inventario-historico.component';
import { InventarioHistoricoDetailComponent } from './inventario-historico-detail.component';
import { InventarioHistoricoUpdateComponent } from './inventario-historico-update.component';
import { InventarioHistoricoDeletePopupComponent } from './inventario-historico-delete-dialog.component';
import { IInventarioHistorico } from 'app/shared/model/inventario-historico.model';

@Injectable()
export class InventarioHistoricoResolve implements Resolve<IInventarioHistorico> {
  constructor(private service: InventarioHistoricoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInventarioHistorico> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<InventarioHistorico>) => response.ok),
        map((inventarioHistorico: HttpResponse<InventarioHistorico>) => inventarioHistorico.body)
      );
    }
    return of(new InventarioHistorico());
  }
}

export const inventarioHistoricoRoute: Routes = [
  {
    path: '',
    component: InventarioHistoricoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.inventarioHistorico.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InventarioHistoricoDetailComponent,
    resolve: {
      inventarioHistorico: InventarioHistoricoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.inventarioHistorico.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InventarioHistoricoUpdateComponent,
    resolve: {
      inventarioHistorico: InventarioHistoricoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.inventarioHistorico.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InventarioHistoricoUpdateComponent,
    resolve: {
      inventarioHistorico: InventarioHistoricoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.inventarioHistorico.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const inventarioHistoricoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InventarioHistoricoDeletePopupComponent,
    resolve: {
      inventarioHistorico: InventarioHistoricoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.inventarioHistorico.home.title'
    },
    canActivate: [UserRouteAccessService]
    //outlet: 'popup'
  }
];
