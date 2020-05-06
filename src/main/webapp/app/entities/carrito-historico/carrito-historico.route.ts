import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CarritoHistorico } from 'app/shared/model/carrito-historico.model';
import { CarritoHistoricoService } from './carrito-historico.service';
import { CarritoHistoricoComponent } from './carrito-historico.component';
import { CarritoHistoricoDetailComponent } from './carrito-historico-detail.component';
import { CarritoHistoricoUpdateComponent } from './carrito-historico-update.component';
import { CarritoHistoricoDeletePopupComponent } from './carrito-historico-delete-dialog.component';
import { ICarritoHistorico } from 'app/shared/model/carrito-historico.model';

@Injectable()
export class CarritoHistoricoResolve implements Resolve<ICarritoHistorico> {
  constructor(private service: CarritoHistoricoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICarritoHistorico> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CarritoHistorico>) => response.ok),
        map((carritoHistorico: HttpResponse<CarritoHistorico>) => carritoHistorico.body)
      );
    }
    return of(new CarritoHistorico());
  }
}

export const carritoHistoricoRoute: Routes = [
  {
    path: '',
    component: CarritoHistoricoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.carritoHistorico.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CarritoHistoricoDetailComponent,
    resolve: {
      carritoHistorico: CarritoHistoricoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.carritoHistorico.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CarritoHistoricoUpdateComponent,
    resolve: {
      carritoHistorico: CarritoHistoricoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.carritoHistorico.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CarritoHistoricoUpdateComponent,
    resolve: {
      carritoHistorico: CarritoHistoricoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.carritoHistorico.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const carritoHistoricoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CarritoHistoricoDeletePopupComponent,
    resolve: {
      carritoHistorico: CarritoHistoricoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.carritoHistorico.home.title'
    },
    canActivate: [UserRouteAccessService]
    //outlet: 'popup'
  }
];
