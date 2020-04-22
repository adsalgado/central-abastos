import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  UnidadMedidaComponent,
  UnidadMedidaDetailComponent,
  UnidadMedidaUpdateComponent,
  UnidadMedidaDeletePopupComponent,
  UnidadMedidaDeleteDialogComponent,
  unidadMedidaRoute,
  unidadMedidaPopupRoute
} from './';

const ENTITY_STATES = [...unidadMedidaRoute, ...unidadMedidaPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UnidadMedidaComponent,
    UnidadMedidaDetailComponent,
    UnidadMedidaUpdateComponent,
    UnidadMedidaDeleteDialogComponent,
    UnidadMedidaDeletePopupComponent
  ],
  entryComponents: [
    UnidadMedidaComponent,
    UnidadMedidaUpdateComponent,
    UnidadMedidaDeleteDialogComponent,
    UnidadMedidaDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosUnidadMedidaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
