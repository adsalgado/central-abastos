import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
// import {
//  // QuejaComponent,
//  // QuejaDetailComponent,
//  // QuejaUpdateComponent,
//  // QuejaDeletePopupComponent,
//  // QuejaDeleteDialogComponent,
//   //quejaRoute,
// //quejaPopupRoute
// } from './';

//const ENTITY_STATES = [...quejaRoute, ...quejaPopupRoute];

@NgModule({
  imports: [AbastosSharedModule /*RouterModule.forChild(ENTITY_STATES)*/],
  declarations: [
    /*QuejaComponent, QuejaDetailComponent, QuejaUpdateComponent, QuejaDeleteDialogComponent, QuejaDeletePopupComponent*/
  ],
  entryComponents: [
    /*QuejaComponent, QuejaUpdateComponent, QuejaDeleteDialogComponent, QuejaDeletePopupComponent*/
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosQuejaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
