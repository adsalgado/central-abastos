import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  HistoricoPedidoComponent,
  HistoricoPedidoDetailComponent,
  HistoricoPedidoUpdateComponent,
  HistoricoPedidoDeletePopupComponent,
  HistoricoPedidoDeleteDialogComponent,
  historicoPedidoRoute,
  historicoPedidoPopupRoute
} from './';

const ENTITY_STATES = [...historicoPedidoRoute, ...historicoPedidoPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    HistoricoPedidoComponent,
    HistoricoPedidoDetailComponent,
    HistoricoPedidoUpdateComponent,
    HistoricoPedidoDeleteDialogComponent,
    HistoricoPedidoDeletePopupComponent
  ],
  entryComponents: [
    HistoricoPedidoComponent,
    HistoricoPedidoUpdateComponent,
    HistoricoPedidoDeleteDialogComponent,
    HistoricoPedidoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosHistoricoPedidoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
