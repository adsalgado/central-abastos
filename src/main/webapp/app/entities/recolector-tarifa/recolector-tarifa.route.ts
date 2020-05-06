import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RecolectorTarifa } from 'app/shared/model/recolector-tarifa.model';
import { RecolectorTarifaService } from './recolector-tarifa.service';
import { RecolectorTarifaComponent } from './recolector-tarifa.component';
import { RecolectorTarifaDetailComponent } from './recolector-tarifa-detail.component';
import { RecolectorTarifaUpdateComponent } from './recolector-tarifa-update.component';
import { RecolectorTarifaDeletePopupComponent } from './recolector-tarifa-delete-dialog.component';
import { IRecolectorTarifa } from 'app/shared/model/recolector-tarifa.model';

@Injectable()
export class RecolectorTarifaResolve implements Resolve<IRecolectorTarifa> {
  constructor(private service: RecolectorTarifaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRecolectorTarifa> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RecolectorTarifa>) => response.ok),
        map((recolectorTarifa: HttpResponse<RecolectorTarifa>) => recolectorTarifa.body)
      );
    }
    return of(new RecolectorTarifa());
  }
}

export const recolectorTarifaRoute: Routes = [
  {
    path: '',
    component: RecolectorTarifaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.recolectorTarifa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RecolectorTarifaDetailComponent,
    resolve: {
      recolectorTarifa: RecolectorTarifaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.recolectorTarifa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RecolectorTarifaUpdateComponent,
    resolve: {
      recolectorTarifa: RecolectorTarifaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.recolectorTarifa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RecolectorTarifaUpdateComponent,
    resolve: {
      recolectorTarifa: RecolectorTarifaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.recolectorTarifa.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const recolectorTarifaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RecolectorTarifaDeletePopupComponent,
    resolve: {
      recolectorTarifa: RecolectorTarifaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.recolectorTarifa.home.title'
    },
    canActivate: [UserRouteAccessService]
    //outlet: 'popup'
  }
];
