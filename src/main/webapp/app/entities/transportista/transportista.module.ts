import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  TransportistaComponent,
  TransportistaDetailComponent,
  TransportistaUpdateComponent,
  TransportistaDeletePopupComponent,
  TransportistaDeleteDialogComponent,
  transportistaRoute,
  transportistaPopupRoute
} from './';

const ENTITY_STATES = [...transportistaRoute, ...transportistaPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TransportistaComponent,
    TransportistaDetailComponent,
    TransportistaUpdateComponent,
    TransportistaDeleteDialogComponent,
    TransportistaDeletePopupComponent
  ],
  entryComponents: [
    TransportistaComponent,
    TransportistaUpdateComponent,
    TransportistaDeleteDialogComponent,
    TransportistaDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosTransportistaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
