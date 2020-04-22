import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  CarritoCompraComponent,
  CarritoCompraDetailComponent,
  CarritoCompraUpdateComponent,
  CarritoCompraDeletePopupComponent,
  CarritoCompraDeleteDialogComponent,
  carritoCompraRoute,
  carritoCompraPopupRoute
} from './';

const ENTITY_STATES = [...carritoCompraRoute, ...carritoCompraPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CarritoCompraComponent,
    CarritoCompraDetailComponent,
    CarritoCompraUpdateComponent,
    CarritoCompraDeleteDialogComponent,
    CarritoCompraDeletePopupComponent
  ],
  entryComponents: [
    CarritoCompraComponent,
    CarritoCompraUpdateComponent,
    CarritoCompraDeleteDialogComponent,
    CarritoCompraDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosCarritoCompraModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
