import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Queja } from 'app/shared/model/queja.model';
import { QuejaService } from './queja.service';
import { QuejaComponent } from './queja.component';
import { QuejaDetailComponent } from './queja-detail.component';
import { QuejaUpdateComponent } from './queja-update.component';
import { QuejaDeletePopupComponent } from './queja-delete-dialog.component';
import { IQueja } from 'app/shared/model/queja.model';

@Injectable({ providedIn: 'root' })
export class QuejaResolve implements Resolve<IQueja> {
  constructor(private service: QuejaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IQueja> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Queja>) => response.ok),
        map((queja: HttpResponse<Queja>) => queja.body)
      );
    }
    return of(new Queja());
  }
}

export const quejaRoute: Routes = [
  {
    path: '',
    component: QuejaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.queja.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: QuejaDetailComponent,
    resolve: {
      queja: QuejaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.queja.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: QuejaUpdateComponent,
    resolve: {
      queja: QuejaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.queja.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: QuejaUpdateComponent,
    resolve: {
      queja: QuejaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.queja.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const quejaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: QuejaDeletePopupComponent,
    resolve: {
      queja: QuejaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.queja.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
