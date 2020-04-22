import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Adjunto } from 'app/shared/model/adjunto.model';
import { AdjuntoService } from './adjunto.service';
import { AdjuntoComponent } from './adjunto.component';
import { AdjuntoDetailComponent } from './adjunto-detail.component';
import { AdjuntoUpdateComponent } from './adjunto-update.component';
import { AdjuntoDeletePopupComponent } from './adjunto-delete-dialog.component';
import { IAdjunto } from 'app/shared/model/adjunto.model';

@Injectable({ providedIn: 'root' })
export class AdjuntoResolve implements Resolve<IAdjunto> {
  constructor(private service: AdjuntoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAdjunto> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Adjunto>) => response.ok),
        map((adjunto: HttpResponse<Adjunto>) => adjunto.body)
      );
    }
    return of(new Adjunto());
  }
}

export const adjuntoRoute: Routes = [
  {
    path: '',
    component: AdjuntoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.adjunto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AdjuntoDetailComponent,
    resolve: {
      adjunto: AdjuntoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.adjunto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AdjuntoUpdateComponent,
    resolve: {
      adjunto: AdjuntoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.adjunto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AdjuntoUpdateComponent,
    resolve: {
      adjunto: AdjuntoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.adjunto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const adjuntoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AdjuntoDeletePopupComponent,
    resolve: {
      adjunto: AdjuntoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.adjunto.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
