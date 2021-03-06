import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Proveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from './proveedor.service';
import { ProveedorComponent } from './proveedor.component';
import { ProveedorDetailComponent } from './proveedor-detail.component';
import { ProveedorUpdateComponent } from './proveedor-update.component';
import { ProveedorDeletePopupComponent } from './proveedor-delete-dialog.component';
import { IProveedor } from 'app/shared/model/proveedor.model';

@Injectable()
export class ProveedorResolve implements Resolve<IProveedor> {
  constructor(private service: ProveedorService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProveedor> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Proveedor>) => response.ok),
        map((proveedor: HttpResponse<Proveedor>) => proveedor.body)
      );
    }
    return of(new Proveedor());
  }
}

export const proveedorRoute: Routes = [
  {
    path: '',
    component: ProveedorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.proveedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProveedorDetailComponent,
    resolve: {
      proveedor: ProveedorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.proveedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProveedorUpdateComponent,
    resolve: {
      proveedor: ProveedorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.proveedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProveedorUpdateComponent,
    resolve: {
      proveedor: ProveedorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.proveedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const proveedorPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProveedorDeletePopupComponent,
    resolve: {
      proveedor: ProveedorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.proveedor.home.title'
    },
    canActivate: [UserRouteAccessService]
    //outlet: 'popup'
  }
];
