import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Estatus } from 'app/shared/model/estatus.model';
import { EstatusService } from './estatus.service';
import { EstatusComponent } from './estatus.component';
import { EstatusDetailComponent } from './estatus-detail.component';
import { EstatusUpdateComponent } from './estatus-update.component';
import { EstatusDeletePopupComponent } from './estatus-delete-dialog.component';
import { IEstatus } from 'app/shared/model/estatus.model';

@Injectable({ providedIn: 'root' })
export class EstatusResolve implements Resolve<IEstatus> {
  constructor(private service: EstatusService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEstatus> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Estatus>) => response.ok),
        map((estatus: HttpResponse<Estatus>) => estatus.body)
      );
    }
    return of(new Estatus());
  }
}

export const estatusRoute: Routes = [
  {
    path: '',
    component: EstatusComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.estatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstatusDetailComponent,
    resolve: {
      estatus: EstatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.estatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstatusUpdateComponent,
    resolve: {
      estatus: EstatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.estatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstatusUpdateComponent,
    resolve: {
      estatus: EstatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.estatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const estatusPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EstatusDeletePopupComponent,
    resolve: {
      estatus: EstatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.estatus.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
