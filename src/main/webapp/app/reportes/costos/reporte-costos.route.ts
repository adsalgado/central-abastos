import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Proveedor } from 'app/shared/model/proveedor.model';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/proveedor.service';
import { ReporteCostosComponent } from './reporte-costos.component';

@Injectable()
export class ReporteCostosResolve implements Resolve<IProveedor> {
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

export const reporteCostosRoute: Routes = [
  {
    path: '',
    component: ReporteCostosComponent,
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'abastosApp.proveedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
