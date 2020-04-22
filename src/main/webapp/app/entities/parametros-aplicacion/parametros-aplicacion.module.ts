import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  ParametrosAplicacionComponent,
  ParametrosAplicacionDetailComponent,
  ParametrosAplicacionUpdateComponent,
  ParametrosAplicacionDeletePopupComponent,
  ParametrosAplicacionDeleteDialogComponent,
  parametrosAplicacionRoute,
  parametrosAplicacionPopupRoute
} from './';

const ENTITY_STATES = [...parametrosAplicacionRoute, ...parametrosAplicacionPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ParametrosAplicacionComponent,
    ParametrosAplicacionDetailComponent,
    ParametrosAplicacionUpdateComponent,
    ParametrosAplicacionDeleteDialogComponent,
    ParametrosAplicacionDeletePopupComponent
  ],
  entryComponents: [
    ParametrosAplicacionComponent,
    ParametrosAplicacionUpdateComponent,
    ParametrosAplicacionDeleteDialogComponent,
    ParametrosAplicacionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosParametrosAplicacionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
