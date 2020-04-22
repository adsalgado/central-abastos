import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  EstatusComponent,
  EstatusDetailComponent,
  EstatusUpdateComponent,
  EstatusDeletePopupComponent,
  EstatusDeleteDialogComponent,
  estatusRoute,
  estatusPopupRoute
} from './';

const ENTITY_STATES = [...estatusRoute, ...estatusPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EstatusComponent,
    EstatusDetailComponent,
    EstatusUpdateComponent,
    EstatusDeleteDialogComponent,
    EstatusDeletePopupComponent
  ],
  entryComponents: [EstatusComponent, EstatusUpdateComponent, EstatusDeleteDialogComponent, EstatusDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosEstatusModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
