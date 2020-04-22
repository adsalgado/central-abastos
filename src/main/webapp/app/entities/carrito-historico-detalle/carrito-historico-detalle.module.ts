import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  CarritoHistoricoDetalleComponent,
  CarritoHistoricoDetalleDetailComponent,
  CarritoHistoricoDetalleUpdateComponent,
  CarritoHistoricoDetalleDeletePopupComponent,
  CarritoHistoricoDetalleDeleteDialogComponent,
  carritoHistoricoDetalleRoute,
  carritoHistoricoDetallePopupRoute
} from './';

const ENTITY_STATES = [...carritoHistoricoDetalleRoute, ...carritoHistoricoDetallePopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CarritoHistoricoDetalleComponent,
    CarritoHistoricoDetalleDetailComponent,
    CarritoHistoricoDetalleUpdateComponent,
    CarritoHistoricoDetalleDeleteDialogComponent,
    CarritoHistoricoDetalleDeletePopupComponent
  ],
  entryComponents: [
    CarritoHistoricoDetalleComponent,
    CarritoHistoricoDetalleUpdateComponent,
    CarritoHistoricoDetalleDeleteDialogComponent,
    CarritoHistoricoDetalleDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosCarritoHistoricoDetalleModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
