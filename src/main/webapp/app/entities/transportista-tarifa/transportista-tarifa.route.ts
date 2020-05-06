import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TransportistaTarifa } from 'app/shared/model/transportista-tarifa.model';
import { TransportistaTarifaService } from './transportista-tarifa.service';
import { TransportistaTarifaComponent } from './transportista-tarifa.component';
import { TransportistaTarifaDetailComponent } from './transportista-tarifa-detail.component';
import { TransportistaTarifaUpdateComponent } from './transportista-tarifa-update.component';
import { TransportistaTarifaDeletePopupComponent } from './transportista-tarifa-delete-dialog.component';
import { ITransportistaTarifa } from 'app/shared/model/transportista-tarifa.model';

@Injectable()
export class TransportistaTarifaResolve implements Resolve<ITransportistaTarifa> {
  constructor(private service: TransportistaTarifaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITransportistaTarifa> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TransportistaTarifa>) => response.ok),
        map((transportistaTarifa: HttpResponse<TransportistaTarifa>) => transportistaTarifa.body)
      );
    }
    return of(new TransportistaTarifa());
  }
}

export const transportistaTarifaRoute: Routes = [
  {
    path: '',
    component: TransportistaTarifaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.transportistaTarifa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransportistaTarifaDetailComponent,
    resolve: {
      transportistaTarifa: TransportistaTarifaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.transportistaTarifa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransportistaTarifaUpdateComponent,
    resolve: {
      transportistaTarifa: TransportistaTarifaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.transportistaTarifa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransportistaTarifaUpdateComponent,
    resolve: {
      transportistaTarifa: TransportistaTarifaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.transportistaTarifa.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const transportistaTarifaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TransportistaTarifaDeletePopupComponent,
    resolve: {
      transportistaTarifa: TransportistaTarifaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.transportistaTarifa.home.title'
    },
    canActivate: [UserRouteAccessService]
    //outlet: 'popup'
  }
];
