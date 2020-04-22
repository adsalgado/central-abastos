import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Recolector } from 'app/shared/model/recolector.model';
import { RecolectorService } from './recolector.service';
import { RecolectorComponent } from './recolector.component';
import { RecolectorDetailComponent } from './recolector-detail.component';
import { RecolectorUpdateComponent } from './recolector-update.component';
import { RecolectorDeletePopupComponent } from './recolector-delete-dialog.component';
import { IRecolector } from 'app/shared/model/recolector.model';

@Injectable({ providedIn: 'root' })
export class RecolectorResolve implements Resolve<IRecolector> {
  constructor(private service: RecolectorService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRecolector> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Recolector>) => response.ok),
        map((recolector: HttpResponse<Recolector>) => recolector.body)
      );
    }
    return of(new Recolector());
  }
}

export const recolectorRoute: Routes = [
  {
    path: '',
    component: RecolectorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.recolector.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RecolectorDetailComponent,
    resolve: {
      recolector: RecolectorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.recolector.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RecolectorUpdateComponent,
    resolve: {
      recolector: RecolectorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.recolector.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RecolectorUpdateComponent,
    resolve: {
      recolector: RecolectorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.recolector.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const recolectorPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RecolectorDeletePopupComponent,
    resolve: {
      recolector: RecolectorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.recolector.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
