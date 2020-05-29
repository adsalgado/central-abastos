import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  PromocionComponent,
  PromocionDetailComponent,
  PromocionUpdateComponent,
  PromocionDeletePopupComponent,
  PromocionDeleteDialogComponent,
  promocionRoute,
  promocionPopupRoute
} from './';

const ENTITY_STATES = [...promocionRoute, ...promocionPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PromocionComponent,
    PromocionDetailComponent,
    PromocionUpdateComponent,
    PromocionDeleteDialogComponent,
    PromocionDeletePopupComponent
  ],
  entryComponents: [PromocionComponent, PromocionUpdateComponent, PromocionDeleteDialogComponent, PromocionDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosPromocionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
