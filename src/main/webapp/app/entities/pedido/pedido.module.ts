import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  PedidoComponent,
  PedidoDetailComponent,
  PedidoUpdateComponent,
  PedidoDeletePopupComponent,
  PedidoDeleteDialogComponent,
  pedidoRoute,
  pedidoPopupRoute
} from './';

const ENTITY_STATES = [...pedidoRoute, ...pedidoPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PedidoComponent, PedidoDetailComponent, PedidoUpdateComponent, PedidoDeleteDialogComponent, PedidoDeletePopupComponent],
  entryComponents: [PedidoComponent, PedidoUpdateComponent, PedidoDeleteDialogComponent, PedidoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosPedidoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
