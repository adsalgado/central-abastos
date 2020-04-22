import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  RecolectorComponent,
  RecolectorDetailComponent,
  RecolectorUpdateComponent,
  RecolectorDeletePopupComponent,
  RecolectorDeleteDialogComponent,
  recolectorRoute,
  recolectorPopupRoute
} from './';

const ENTITY_STATES = [...recolectorRoute, ...recolectorPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RecolectorComponent,
    RecolectorDetailComponent,
    RecolectorUpdateComponent,
    RecolectorDeleteDialogComponent,
    RecolectorDeletePopupComponent
  ],
  entryComponents: [RecolectorComponent, RecolectorUpdateComponent, RecolectorDeleteDialogComponent, RecolectorDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosRecolectorModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
