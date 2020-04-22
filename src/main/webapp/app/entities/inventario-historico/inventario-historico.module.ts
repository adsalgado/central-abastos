import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  InventarioHistoricoComponent,
  InventarioHistoricoDetailComponent,
  InventarioHistoricoUpdateComponent,
  InventarioHistoricoDeletePopupComponent,
  InventarioHistoricoDeleteDialogComponent,
  inventarioHistoricoRoute,
  inventarioHistoricoPopupRoute
} from './';

const ENTITY_STATES = [...inventarioHistoricoRoute, ...inventarioHistoricoPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InventarioHistoricoComponent,
    InventarioHistoricoDetailComponent,
    InventarioHistoricoUpdateComponent,
    InventarioHistoricoDeleteDialogComponent,
    InventarioHistoricoDeletePopupComponent
  ],
  entryComponents: [
    InventarioHistoricoComponent,
    InventarioHistoricoUpdateComponent,
    InventarioHistoricoDeleteDialogComponent,
    InventarioHistoricoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosInventarioHistoricoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
