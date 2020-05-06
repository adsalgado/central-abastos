import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UsuarioImagen } from 'app/shared/model/usuario-imagen.model';
import { UsuarioImagenService } from './usuario-imagen.service';
import { UsuarioImagenComponent } from './usuario-imagen.component';
import { UsuarioImagenDetailComponent } from './usuario-imagen-detail.component';
import { UsuarioImagenUpdateComponent } from './usuario-imagen-update.component';
import { UsuarioImagenDeletePopupComponent } from './usuario-imagen-delete-dialog.component';
import { IUsuarioImagen } from 'app/shared/model/usuario-imagen.model';

@Injectable()
export class UsuarioImagenResolve implements Resolve<IUsuarioImagen> {
  constructor(private service: UsuarioImagenService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUsuarioImagen> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<UsuarioImagen>) => response.ok),
        map((usuarioImagen: HttpResponse<UsuarioImagen>) => usuarioImagen.body)
      );
    }
    return of(new UsuarioImagen());
  }
}

export const usuarioImagenRoute: Routes = [
  {
    path: '',
    component: UsuarioImagenComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.usuarioImagen.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UsuarioImagenDetailComponent,
    resolve: {
      usuarioImagen: UsuarioImagenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.usuarioImagen.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UsuarioImagenUpdateComponent,
    resolve: {
      usuarioImagen: UsuarioImagenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.usuarioImagen.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UsuarioImagenUpdateComponent,
    resolve: {
      usuarioImagen: UsuarioImagenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.usuarioImagen.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const usuarioImagenPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: UsuarioImagenDeletePopupComponent,
    resolve: {
      usuarioImagen: UsuarioImagenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.usuarioImagen.home.title'
    },
    canActivate: [UserRouteAccessService]
    //outlet: 'popup'
  }
];
