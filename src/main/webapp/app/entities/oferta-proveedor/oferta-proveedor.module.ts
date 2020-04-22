import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  OfertaProveedorComponent,
  OfertaProveedorDetailComponent,
  OfertaProveedorUpdateComponent,
  OfertaProveedorDeletePopupComponent,
  OfertaProveedorDeleteDialogComponent,
  ofertaProveedorRoute,
  ofertaProveedorPopupRoute
} from './';

const ENTITY_STATES = [...ofertaProveedorRoute, ...ofertaProveedorPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OfertaProveedorComponent,
    OfertaProveedorDetailComponent,
    OfertaProveedorUpdateComponent,
    OfertaProveedorDeleteDialogComponent,
    OfertaProveedorDeletePopupComponent
  ],
  entryComponents: [
    OfertaProveedorComponent,
    OfertaProveedorUpdateComponent,
    OfertaProveedorDeleteDialogComponent,
    OfertaProveedorDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosOfertaProveedorModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
