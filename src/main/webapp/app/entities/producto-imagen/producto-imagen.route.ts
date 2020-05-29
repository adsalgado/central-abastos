import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductoImagen } from 'app/shared/model/producto-imagen.model';
import { ProductoImagenService } from './producto-imagen.service';
import { ProductoImagenComponent } from './producto-imagen.component';
import { ProductoImagenDetailComponent } from './producto-imagen-detail.component';
import { ProductoImagenUpdateComponent } from './producto-imagen-update.component';
import { ProductoImagenDeletePopupComponent } from './producto-imagen-delete-dialog.component';
import { IProductoImagen } from 'app/shared/model/producto-imagen.model';

@Injectable()
export class ProductoImagenResolve implements Resolve<IProductoImagen> {
  constructor(private service: ProductoImagenService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductoImagen> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProductoImagen>) => response.ok),
        map((productoImagen: HttpResponse<ProductoImagen>) => productoImagen.body)
      );
    }
    return of(new ProductoImagen());
  }
}

export const productoImagenRoute: Routes = [
  {
    path: 'producto-proveedor/:productoProveedorId',
    component: ProductoImagenComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.productoImagen.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'producto-proveedor/:productoProveedorId/:id/view',
    component: ProductoImagenDetailComponent,
    resolve: {
      productoImagen: ProductoImagenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.productoImagen.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'producto-proveedor/:productoProveedorId/new',
    component: ProductoImagenUpdateComponent,
    resolve: {
      productoImagen: ProductoImagenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.productoImagen.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'producto-proveedor/:productoProveedorId/:id/edit',
    component: ProductoImagenUpdateComponent,
    resolve: {
      productoImagen: ProductoImagenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.productoImagen.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productoImagenPopupRoute: Routes = [
  {
    path: 'producto-proveedor/:productoProveedorId/:id/delete',
    component: ProductoImagenDeletePopupComponent,
    resolve: {
      productoImagen: ProductoImagenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.productoImagen.home.title'
    },
    canActivate: [UserRouteAccessService]
    //outlet: 'popup'
  }
];
