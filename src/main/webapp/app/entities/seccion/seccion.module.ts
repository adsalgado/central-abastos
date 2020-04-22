import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  SeccionComponent,
  SeccionDetailComponent,
  SeccionUpdateComponent,
  SeccionDeletePopupComponent,
  SeccionDeleteDialogComponent,
  seccionRoute,
  seccionPopupRoute
} from './';

const ENTITY_STATES = [...seccionRoute, ...seccionPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SeccionComponent,
    SeccionDetailComponent,
    SeccionUpdateComponent,
    SeccionDeleteDialogComponent,
    SeccionDeletePopupComponent
  ],
  entryComponents: [SeccionComponent, SeccionUpdateComponent, SeccionDeleteDialogComponent, SeccionDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosSeccionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
