import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductoProveedor } from 'app/shared/model/producto-proveedor.model';
import { ProductoProveedorService } from './producto-proveedor.service';
import { ProductoProveedorComponent } from './producto-proveedor.component';
import { ProductoProveedorDetailComponent } from './producto-proveedor-detail.component';
import { ProductoProveedorUpdateComponent } from './producto-proveedor-update.component';
import { ProductoProveedorDeletePopupComponent } from './producto-proveedor-delete-dialog.component';
import { IProductoProveedor } from 'app/shared/model/producto-proveedor.model';
import { ProductoProveedorCargaMasivaComponent } from './producto-proveedor-carga-masiva.component';

@Injectable({ providedIn: 'root' })
export class ProductoProveedorResolve implements Resolve<IProductoProveedor> {
  constructor(private service: ProductoProveedorService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductoProveedor> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProductoProveedor>) => response.ok),
        map((productoProveedor: HttpResponse<ProductoProveedor>) => productoProveedor.body)
      );
    }
    return of(new ProductoProveedor());
  }
}

export const productoProveedorRoute: Routes = [
  {
    path: '',
    component: ProductoProveedorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.productoProveedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductoProveedorDetailComponent,
    resolve: {
      productoProveedor: ProductoProveedorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.productoProveedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductoProveedorUpdateComponent,
    resolve: {
      productoProveedor: ProductoProveedorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.productoProveedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductoProveedorUpdateComponent,
    resolve: {
      productoProveedor: ProductoProveedorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.productoProveedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'carga-masiva',
    component: ProductoProveedorCargaMasivaComponent,
    resolve: {
      productoProveedor: ProductoProveedorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.productoProveedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productoProveedorPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProductoProveedorDeletePopupComponent,
    resolve: {
      productoProveedor: ProductoProveedorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.productoProveedor.home.title'
    },
    canActivate: [UserRouteAccessService]
    //outlet: 'popup'
  }
];
