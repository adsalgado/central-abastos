import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  CarritoHistoricoComponent,
  CarritoHistoricoDetailComponent,
  CarritoHistoricoUpdateComponent,
  CarritoHistoricoDeletePopupComponent,
  CarritoHistoricoDeleteDialogComponent,
  carritoHistoricoRoute,
  carritoHistoricoPopupRoute
} from './';

const ENTITY_STATES = [...carritoHistoricoRoute, ...carritoHistoricoPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CarritoHistoricoComponent,
    CarritoHistoricoDetailComponent,
    CarritoHistoricoUpdateComponent,
    CarritoHistoricoDeleteDialogComponent,
    CarritoHistoricoDeletePopupComponent
  ],
  entryComponents: [
    CarritoHistoricoComponent,
    CarritoHistoricoUpdateComponent,
    CarritoHistoricoDeleteDialogComponent,
    CarritoHistoricoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosCarritoHistoricoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
