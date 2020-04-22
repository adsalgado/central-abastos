import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OfertaProveedor } from 'app/shared/model/oferta-proveedor.model';
import { OfertaProveedorService } from './oferta-proveedor.service';
import { OfertaProveedorComponent } from './oferta-proveedor.component';
import { OfertaProveedorDetailComponent } from './oferta-proveedor-detail.component';
import { OfertaProveedorUpdateComponent } from './oferta-proveedor-update.component';
import { OfertaProveedorDeletePopupComponent } from './oferta-proveedor-delete-dialog.component';
import { IOfertaProveedor } from 'app/shared/model/oferta-proveedor.model';

@Injectable({ providedIn: 'root' })
export class OfertaProveedorResolve implements Resolve<IOfertaProveedor> {
  constructor(private service: OfertaProveedorService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOfertaProveedor> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OfertaProveedor>) => response.ok),
        map((ofertaProveedor: HttpResponse<OfertaProveedor>) => ofertaProveedor.body)
      );
    }
    return of(new OfertaProveedor());
  }
}

export const ofertaProveedorRoute: Routes = [
  {
    path: '',
    component: OfertaProveedorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.ofertaProveedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OfertaProveedorDetailComponent,
    resolve: {
      ofertaProveedor: OfertaProveedorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.ofertaProveedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OfertaProveedorUpdateComponent,
    resolve: {
      ofertaProveedor: OfertaProveedorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.ofertaProveedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OfertaProveedorUpdateComponent,
    resolve: {
      ofertaProveedor: OfertaProveedorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.ofertaProveedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const ofertaProveedorPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OfertaProveedorDeletePopupComponent,
    resolve: {
      ofertaProveedor: OfertaProveedorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.ofertaProveedor.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
