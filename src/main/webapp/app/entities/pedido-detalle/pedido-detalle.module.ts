import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  PedidoDetalleComponent,
  PedidoDetalleDetailComponent,
  PedidoDetalleUpdateComponent,
  PedidoDetalleDeletePopupComponent,
  PedidoDetalleDeleteDialogComponent,
  pedidoDetalleRoute,
  pedidoDetallePopupRoute
} from './';

const ENTITY_STATES = [...pedidoDetalleRoute, ...pedidoDetallePopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PedidoDetalleComponent,
    PedidoDetalleDetailComponent,
    PedidoDetalleUpdateComponent,
    PedidoDetalleDeleteDialogComponent,
    PedidoDetalleDeletePopupComponent
  ],
  entryComponents: [
    PedidoDetalleComponent,
    PedidoDetalleUpdateComponent,
    PedidoDetalleDeleteDialogComponent,
    PedidoDetalleDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosPedidoDetalleModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
