import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  NotificacionComponent,
  NotificacionDetailComponent,
  NotificacionUpdateComponent,
  NotificacionDeletePopupComponent,
  NotificacionDeleteDialogComponent,
  notificacionRoute,
  notificacionPopupRoute
} from './';

const ENTITY_STATES = [...notificacionRoute, ...notificacionPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    NotificacionComponent,
    NotificacionDetailComponent,
    NotificacionUpdateComponent,
    NotificacionDeleteDialogComponent,
    NotificacionDeletePopupComponent
  ],
  entryComponents: [
    NotificacionComponent,
    NotificacionUpdateComponent,
    NotificacionDeleteDialogComponent,
    NotificacionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosNotificacionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
