import { AlertService } from './alert.service';
import { LocalStorageEncryptService } from './local-storage-encrypt-service';
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable()
export class AutenticateService implements CanActivate {
  constructor(private localStorageEncryptService: LocalStorageEncryptService, private router: Router, private alertService: AlertService) {}

  canActivate() {
    let userSession: any = this.localStorageEncryptService.getFromLocalStorage('userSession');

    if (!userSession) {
      this.alertService.warn('Por favor inicie sesión');
      this.router.navigate(['login']);
    }

    return userSession ? true : false;
  }
}
