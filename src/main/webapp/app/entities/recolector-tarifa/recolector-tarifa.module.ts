import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  RecolectorTarifaComponent,
  RecolectorTarifaDetailComponent,
  RecolectorTarifaUpdateComponent,
  RecolectorTarifaDeletePopupComponent,
  RecolectorTarifaDeleteDialogComponent,
  recolectorTarifaRoute,
  recolectorTarifaPopupRoute
} from './';

const ENTITY_STATES = [...recolectorTarifaRoute, ...recolectorTarifaPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RecolectorTarifaComponent,
    RecolectorTarifaDetailComponent,
    RecolectorTarifaUpdateComponent,
    RecolectorTarifaDeleteDialogComponent,
    RecolectorTarifaDeletePopupComponent
  ],
  entryComponents: [
    RecolectorTarifaComponent,
    RecolectorTarifaUpdateComponent,
    RecolectorTarifaDeleteDialogComponent,
    RecolectorTarifaDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosRecolectorTarifaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
