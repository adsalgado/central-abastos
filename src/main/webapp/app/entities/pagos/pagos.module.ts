import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  PagosComponent,
  PagosDetailComponent,
  PagosUpdateComponent,
  PagosDeletePopupComponent,
  PagosDeleteDialogComponent,
  pagosRoute,
  pagosPopupRoute
} from './';

const ENTITY_STATES = [...pagosRoute, ...pagosPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PagosComponent, PagosDetailComponent, PagosUpdateComponent, PagosDeleteDialogComponent, PagosDeletePopupComponent],
  entryComponents: [PagosComponent, PagosUpdateComponent, PagosDeleteDialogComponent, PagosDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosPagosModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
