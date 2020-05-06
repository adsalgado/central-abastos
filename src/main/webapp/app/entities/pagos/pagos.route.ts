import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Pagos } from 'app/shared/model/pagos.model';
import { PagosService } from './pagos.service';
import { PagosComponent } from './pagos.component';
import { PagosDetailComponent } from './pagos-detail.component';
import { PagosUpdateComponent } from './pagos-update.component';
import { PagosDeletePopupComponent } from './pagos-delete-dialog.component';
import { IPagos } from 'app/shared/model/pagos.model';

@Injectable()
export class PagosResolve implements Resolve<IPagos> {
  constructor(private service: PagosService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPagos> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Pagos>) => response.ok),
        map((pagos: HttpResponse<Pagos>) => pagos.body)
      );
    }
    return of(new Pagos());
  }
}

export const pagosRoute: Routes = [
  {
    path: '',
    component: PagosComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.pagos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PagosDetailComponent,
    resolve: {
      pagos: PagosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.pagos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PagosUpdateComponent,
    resolve: {
      pagos: PagosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.pagos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PagosUpdateComponent,
    resolve: {
      pagos: PagosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.pagos.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pagosPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PagosDeletePopupComponent,
    resolve: {
      pagos: PagosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.pagos.home.title'
    },
    canActivate: [UserRouteAccessService]
    //outlet: 'popup'
  }
];
