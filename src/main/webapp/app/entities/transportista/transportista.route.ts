import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Transportista } from 'app/shared/model/transportista.model';
import { TransportistaService } from './transportista.service';
import { TransportistaComponent } from './transportista.component';
import { TransportistaDetailComponent } from './transportista-detail.component';
import { TransportistaUpdateComponent } from './transportista-update.component';
import { TransportistaDeletePopupComponent } from './transportista-delete-dialog.component';
import { ITransportista } from 'app/shared/model/transportista.model';

@Injectable()
export class TransportistaResolve implements Resolve<ITransportista> {
  constructor(private service: TransportistaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITransportista> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Transportista>) => response.ok),
        map((transportista: HttpResponse<Transportista>) => transportista.body)
      );
    }
    return of(new Transportista());
  }
}

export const transportistaRoute: Routes = [
  {
    path: '',
    component: TransportistaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.transportista.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransportistaDetailComponent,
    resolve: {
      transportista: TransportistaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.transportista.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransportistaUpdateComponent,
    resolve: {
      transportista: TransportistaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.transportista.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransportistaUpdateComponent,
    resolve: {
      transportista: TransportistaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.transportista.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const transportistaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TransportistaDeletePopupComponent,
    resolve: {
      transportista: TransportistaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.transportista.home.title'
    },
    canActivate: [UserRouteAccessService]
    //outlet: 'popup'
  }
];
