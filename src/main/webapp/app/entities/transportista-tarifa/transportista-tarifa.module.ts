import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  TransportistaTarifaComponent,
  TransportistaTarifaDetailComponent,
  TransportistaTarifaUpdateComponent,
  TransportistaTarifaDeletePopupComponent,
  TransportistaTarifaDeleteDialogComponent,
  transportistaTarifaRoute,
  transportistaTarifaPopupRoute
} from './';

const ENTITY_STATES = [...transportistaTarifaRoute, ...transportistaTarifaPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TransportistaTarifaComponent,
    TransportistaTarifaDetailComponent,
    TransportistaTarifaUpdateComponent,
    TransportistaTarifaDeleteDialogComponent,
    TransportistaTarifaDeletePopupComponent
  ],
  entryComponents: [
    TransportistaTarifaComponent,
    TransportistaTarifaUpdateComponent,
    TransportistaTarifaDeleteDialogComponent,
    TransportistaTarifaDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosTransportistaTarifaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
