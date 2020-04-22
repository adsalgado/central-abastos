import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  TipoOfertaComponent,
  TipoOfertaDetailComponent,
  TipoOfertaUpdateComponent,
  TipoOfertaDeletePopupComponent,
  TipoOfertaDeleteDialogComponent,
  tipoOfertaRoute,
  tipoOfertaPopupRoute
} from './';

const ENTITY_STATES = [...tipoOfertaRoute, ...tipoOfertaPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TipoOfertaComponent,
    TipoOfertaDetailComponent,
    TipoOfertaUpdateComponent,
    TipoOfertaDeleteDialogComponent,
    TipoOfertaDeletePopupComponent
  ],
  entryComponents: [TipoOfertaComponent, TipoOfertaUpdateComponent, TipoOfertaDeleteDialogComponent, TipoOfertaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosTipoOfertaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
