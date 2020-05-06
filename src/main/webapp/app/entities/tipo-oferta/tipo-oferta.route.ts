import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TipoOferta } from 'app/shared/model/tipo-oferta.model';
import { TipoOfertaService } from './tipo-oferta.service';
import { TipoOfertaComponent } from './tipo-oferta.component';
import { TipoOfertaDetailComponent } from './tipo-oferta-detail.component';
import { TipoOfertaUpdateComponent } from './tipo-oferta-update.component';
import { TipoOfertaDeletePopupComponent } from './tipo-oferta-delete-dialog.component';
import { ITipoOferta } from 'app/shared/model/tipo-oferta.model';

@Injectable()
export class TipoOfertaResolve implements Resolve<ITipoOferta> {
  constructor(private service: TipoOfertaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITipoOferta> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TipoOferta>) => response.ok),
        map((tipoOferta: HttpResponse<TipoOferta>) => tipoOferta.body)
      );
    }
    return of(new TipoOferta());
  }
}

export const tipoOfertaRoute: Routes = [
  {
    path: '',
    component: TipoOfertaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.tipoOferta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoOfertaDetailComponent,
    resolve: {
      tipoOferta: TipoOfertaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.tipoOferta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoOfertaUpdateComponent,
    resolve: {
      tipoOferta: TipoOfertaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.tipoOferta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoOfertaUpdateComponent,
    resolve: {
      tipoOferta: TipoOfertaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.tipoOferta.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tipoOfertaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TipoOfertaDeletePopupComponent,
    resolve: {
      tipoOferta: TipoOfertaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.tipoOferta.home.title'
    },
    canActivate: [UserRouteAccessService]
    //outlet: 'popup'
  }
];
