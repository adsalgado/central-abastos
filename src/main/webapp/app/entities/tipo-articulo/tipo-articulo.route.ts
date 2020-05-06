import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TipoArticulo } from 'app/shared/model/tipo-articulo.model';
import { TipoArticuloService } from './tipo-articulo.service';
import { TipoArticuloComponent } from './tipo-articulo.component';
import { TipoArticuloDetailComponent } from './tipo-articulo-detail.component';
import { TipoArticuloUpdateComponent } from './tipo-articulo-update.component';
import { TipoArticuloDeletePopupComponent } from './tipo-articulo-delete-dialog.component';
import { ITipoArticulo } from 'app/shared/model/tipo-articulo.model';

@Injectable()
export class TipoArticuloResolve implements Resolve<ITipoArticulo> {
  constructor(private service: TipoArticuloService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITipoArticulo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TipoArticulo>) => response.ok),
        map((tipoArticulo: HttpResponse<TipoArticulo>) => tipoArticulo.body)
      );
    }
    return of(new TipoArticulo());
  }
}

export const tipoArticuloRoute: Routes = [
  {
    path: '',
    component: TipoArticuloComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.tipoArticulo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoArticuloDetailComponent,
    resolve: {
      tipoArticulo: TipoArticuloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.tipoArticulo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoArticuloUpdateComponent,
    resolve: {
      tipoArticulo: TipoArticuloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.tipoArticulo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoArticuloUpdateComponent,
    resolve: {
      tipoArticulo: TipoArticuloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.tipoArticulo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tipoArticuloPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TipoArticuloDeletePopupComponent,
    resolve: {
      tipoArticulo: TipoArticuloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.tipoArticulo.home.title'
    },
    canActivate: [UserRouteAccessService]
    //outlet: 'popup'
  }
];
