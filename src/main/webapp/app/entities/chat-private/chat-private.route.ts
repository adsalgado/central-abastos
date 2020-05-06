import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ChatPrivate } from 'app/shared/model/chat-private.model';
import { ChatPrivateService } from './chat-private.service';
import { ChatPrivateComponent } from './chat-private.component';
import { ChatPrivateDetailComponent } from './chat-private-detail.component';
import { ChatPrivateUpdateComponent } from './chat-private-update.component';
import { ChatPrivateDeletePopupComponent } from './chat-private-delete-dialog.component';
import { IChatPrivate } from 'app/shared/model/chat-private.model';

@Injectable()
export class ChatPrivateResolve implements Resolve<IChatPrivate> {
  constructor(private service: ChatPrivateService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IChatPrivate> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ChatPrivate>) => response.ok),
        map((chatPrivate: HttpResponse<ChatPrivate>) => chatPrivate.body)
      );
    }
    return of(new ChatPrivate());
  }
}

export const chatPrivateRoute: Routes = [
  {
    path: '',
    component: ChatPrivateComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.chatPrivate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ChatPrivateDetailComponent,
    resolve: {
      chatPrivate: ChatPrivateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.chatPrivate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ChatPrivateUpdateComponent,
    resolve: {
      chatPrivate: ChatPrivateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.chatPrivate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ChatPrivateUpdateComponent,
    resolve: {
      chatPrivate: ChatPrivateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.chatPrivate.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const chatPrivatePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ChatPrivateDeletePopupComponent,
    resolve: {
      chatPrivate: ChatPrivateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'abastosApp.chatPrivate.home.title'
    },
    canActivate: [UserRouteAccessService]
    //outlet: 'popup'
  }
];
