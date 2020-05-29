import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Promocion } from 'app/shared/model/promocion.model';
import { PromocionService } from './promocion.service';
import { PromocionComponent } from './promocion.component';
import { PromocionDetailComponent } from './promocion-detail.component';
import { PromocionUpdateComponent } from './promocion-update.component';
import { PromocionDeletePopupComponent } from './promocion-delete-dialog.component';
import { IPromocion } from 'app/shared/model/promocion.model';

@Injectable()
export class PromocionResolve implements Resolve<IPromocion> {
  constructor(private service: PromocionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPromocion> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Promocion>) => response.ok),
        map((promocion: HttpResponse<Promocion>) => promocion.body)
      );
    }
    return of(new Promocion());
  }
}

export const promocionRoute: Routes = [
  {
    path: '',
    component: PromocionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.promocion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PromocionDetailComponent,
    resolve: {
      promocion: PromocionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.promocion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PromocionUpdateComponent,
    resolve: {
      promocion: PromocionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.promocion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PromocionUpdateComponent,
    resolve: {
      promocion: PromocionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.promocion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const promocionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PromocionDeletePopupComponent,
    resolve: {
      promocion: PromocionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.promocion.home.title'
    },
    canActivate: [UserRouteAccessService]
    //outlet: 'popup'
  }
];
