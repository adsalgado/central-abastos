import { AuthService } from './../../services/auth.service';
import { GenericService } from './../../services/generic.service';
import { NavParamsService } from 'app/services/nav-params.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { JhiLanguageHelper, AccountService, LoginModalService, LoginService } from 'app/core';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['navbar.scss']
})
export class NavbarComponent implements OnInit {
  inProduction: boolean;
  isNavbarCollapsed: boolean = false;
  languages: any[];
  swaggerEnabled: boolean;
  modalRef: NgbModalRef;
  version: string;

  public habilitado: boolean = false;
  constructor(
    private loginService: LoginService,
    private languageService: JhiLanguageService,
    private languageHelper: JhiLanguageHelper,
    private sessionStorage: SessionStorageService,
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private profileService: ProfileService,
    private router: Router,
    private navParamsService: NavParamsService,
    private genericService: GenericService,
    private authService: AuthService,
    private localStorageEncryptService: LocalStorageEncryptService
  ) {
    this.version = VERSION ? 'v' + VERSION : '';
    this.isNavbarCollapsed = true;
  }

  ngOnInit() {
    this.languageHelper.getAll().then(languages => {
      this.languages = languages;
    });

    this.profileService.getProfileInfo().then(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.swaggerEnabled = profileInfo.swaggerEnabled;
    });
  }

  changeLanguage(languageKey: string) {
    this.sessionStorage.store('locale', languageKey);
    this.languageService.changeLanguage(languageKey);
  }

  collapseNavbar() {
    this.isNavbarCollapsed = true;
    let user = this.localStorageEncryptService.getFromLocalStorage('userSession');

    if (user.tipo_usuario <= 1 || user.tipo_usuario == 5 || user.tipo_usuario == 3) {
      this.navParamsService.push('/main/pedidos-proveedor');
    } else {
      this.navParamsService.push('/main/public-home');
    }
  }

  isAuthenticated() {
    return this.authService.isAuthenticatedBoolean();
    //return this.accountService.isAuthenticated();
    //return true;
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  logout() {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar() {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;

    this.habilitado = !this.habilitado;
  }

  getImageUrl() {
    //return this.isAuthenticated() ? this.accountService.getImageUrl() : null;
  }
}
