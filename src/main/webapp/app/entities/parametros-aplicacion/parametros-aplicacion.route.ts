import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ParametrosAplicacion } from 'app/shared/model/parametros-aplicacion.model';
import { ParametrosAplicacionService } from './parametros-aplicacion.service';
import { ParametrosAplicacionComponent } from './parametros-aplicacion.component';
import { ParametrosAplicacionDetailComponent } from './parametros-aplicacion-detail.component';
import { ParametrosAplicacionUpdateComponent } from './parametros-aplicacion-update.component';
import { ParametrosAplicacionDeletePopupComponent } from './parametros-aplicacion-delete-dialog.component';
import { IParametrosAplicacion } from 'app/shared/model/parametros-aplicacion.model';

@Injectable()
export class ParametrosAplicacionResolve implements Resolve<IParametrosAplicacion> {
  constructor(private service: ParametrosAplicacionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IParametrosAplicacion> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ParametrosAplicacion>) => response.ok),
        map((parametrosAplicacion: HttpResponse<ParametrosAplicacion>) => parametrosAplicacion.body)
      );
    }
    return of(new ParametrosAplicacion());
  }
}

export const parametrosAplicacionRoute: Routes = [
  {
    path: '',
    component: ParametrosAplicacionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.parametrosAplicacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ParametrosAplicacionDetailComponent,
    resolve: {
      parametrosAplicacion: ParametrosAplicacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.parametrosAplicacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ParametrosAplicacionUpdateComponent,
    resolve: {
      parametrosAplicacion: ParametrosAplicacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.parametrosAplicacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ParametrosAplicacionUpdateComponent,
    resolve: {
      parametrosAplicacion: ParametrosAplicacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.parametrosAplicacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const parametrosAplicacionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ParametrosAplicacionDeletePopupComponent,
    resolve: {
      parametrosAplicacion: ParametrosAplicacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.parametrosAplicacion.home.title'
    },
    canActivate: [UserRouteAccessService]
    //outlet: 'popup'
  }
];
