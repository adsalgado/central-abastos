import { LocalStorageEncryptService } from './../../services/local-storage-encrypt-service';
import { Injectable } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';
import { AuthServerProvider } from 'app/core/auth/auth-jwt.service';
import { JhiTrackerService } from 'app/core/tracker/tracker.service';
import { JhiEventManager } from 'ng-jhipster';

@Injectable()
export class LoginService {
  constructor(
    private accountService: AccountService,
    private trackerService: JhiTrackerService,
    private eventManager: JhiEventManager,
    private authServerProvider: AuthServerProvider,
    private localStorageEncryptService: LocalStorageEncryptService,
    private events: JhiEventManager
  ) {}

  login(credentials, callback?) {
    const cb = callback || function() {};

    return new Promise((resolve, reject) => {
      this.authServerProvider.login(credentials).subscribe(
        data => {
          this.accountService.identity(true).then(account => {
            this.trackerService.sendActivity();
            /* console.log("-----------------");
            console.log(data);
             */
            resolve(data);
          });
          return cb();
        },
        err => {
          this.logout();
          reject(err);
          return cb(err);
        }
      );
    });
  }

  loginWithToken(jwt, rememberMe) {
    return this.authServerProvider.loginWithToken(jwt, rememberMe);
  }

  logout() {
    this.authServerProvider.logout().subscribe(null, null, () => this.accountService.authenticate(null));
    this.localStorageEncryptService.clearProperty('userSession');
    this.events.broadcast({ name: 'intervalando', content: {} });
    this.eventManager.broadcast({
      name: 'logoutSuccess',
      content: 'Sending Logout Success'
    });
  }
}
