import { NavParamsService } from './../../services/nav-params.service';
import { Injectable, isDevMode } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';

import { AccountService } from 'app/core/';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { StateStorageService } from './state-storage.service';
import { JhiLoginModalComponent } from 'app/shared/login/login.component';
import { JhiEventManager } from 'ng-jhipster';

@Injectable()
export class UserRouteAccessService implements CanActivate {
  constructor(
    private router: Router,
    private loginModalService: LoginModalService,
    private accountService: AccountService,
    private stateStorageService: StateStorageService,
    private navParamsService: NavParamsService,
    private eventManager: JhiEventManager
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Promise<boolean> {
    const authorities = route.data['authorities'];
    // We need to call the checkLogin / and so the accountService.identity() function, to ensure,
    // that the client has a principal too, if they already logged in by the server.
    // This could happen on a page refresh.
    return this.checkLogin(authorities, state.url);
  }

  checkLogin(authorities: string[], url: string): Promise<boolean> {
    return this.accountService.identity().then(account => {
      if (!authorities || authorities.length === 0) {
        return true;
      }

      if (account) {
        const hasAnyAuthority = this.accountService.hasAnyAuthority(authorities);
        if (hasAnyAuthority) {
          return true;
        }
        if (isDevMode()) {
          console.error('User has not any of required authorities: ', authorities);
        }
        this.router.navigate(['accessdenied']);
        return false;
      }

      //this.stateStorageService.storeUrl(url);
      //this.loginModalService.open();
      //this.navParamsService.push('main/login');
      //this.eventManager.broadcast({ name: "startSession", content: {} });
      return false;
    });
  }
}
