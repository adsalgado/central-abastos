import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  TipoArticuloComponent,
  TipoArticuloDetailComponent,
  TipoArticuloUpdateComponent,
  TipoArticuloDeletePopupComponent,
  TipoArticuloDeleteDialogComponent,
  tipoArticuloRoute,
  tipoArticuloPopupRoute
} from './';

const ENTITY_STATES = [...tipoArticuloRoute, ...tipoArticuloPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TipoArticuloComponent,
    TipoArticuloDetailComponent,
    TipoArticuloUpdateComponent,
    TipoArticuloDeleteDialogComponent,
    TipoArticuloDeletePopupComponent
  ],
  entryComponents: [
    TipoArticuloComponent,
    TipoArticuloUpdateComponent,
    TipoArticuloDeleteDialogComponent,
    TipoArticuloDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosTipoArticuloModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
