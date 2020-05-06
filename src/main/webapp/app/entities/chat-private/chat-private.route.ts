import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { ChatPrivateService } from './chat-private.service';
import { ChatPrivateComponent } from './chat-private.component';

@Injectable()
export class ChatPrivateResolve {
  constructor(private service: ChatPrivateService) {}
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
  }
];
