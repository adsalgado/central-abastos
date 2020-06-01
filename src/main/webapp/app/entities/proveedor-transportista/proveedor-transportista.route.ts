import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Proveedor } from 'app/shared/model/proveedor.model';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from '../proveedor/proveedor.service';
import { ProveedorTransportistaUpdateComponent } from './proveedor-transportista-update.component';

@Injectable()
export class ProveedorTransportistaResolve implements Resolve<IProveedor> {
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

export const proveedorTransportistaRoute: Routes = [
  {
    path: '',
    component: ProveedorTransportistaUpdateComponent,
    resolve: {
      proveedor: ProveedorTransportistaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.proveedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProveedorTransportistaUpdateComponent,
    resolve: {
      proveedor: ProveedorTransportistaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.proveedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
