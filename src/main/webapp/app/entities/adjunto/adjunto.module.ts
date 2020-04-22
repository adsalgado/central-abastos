import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  AdjuntoComponent,
  AdjuntoDetailComponent,
  AdjuntoUpdateComponent,
  AdjuntoDeletePopupComponent,
  AdjuntoDeleteDialogComponent,
  adjuntoRoute,
  adjuntoPopupRoute
} from './';

const ENTITY_STATES = [...adjuntoRoute, ...adjuntoPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AdjuntoComponent,
    AdjuntoDetailComponent,
    AdjuntoUpdateComponent,
    AdjuntoDeleteDialogComponent,
    AdjuntoDeletePopupComponent
  ],
  entryComponents: [AdjuntoComponent, AdjuntoUpdateComponent, AdjuntoDeleteDialogComponent, AdjuntoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosAdjuntoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
