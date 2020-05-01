import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  ChatPrivateComponent,
  ChatPrivateDetailComponent,
  ChatPrivateUpdateComponent,
  ChatPrivateDeletePopupComponent,
  ChatPrivateDeleteDialogComponent,
  chatPrivateRoute,
  chatPrivatePopupRoute
} from './';

const ENTITY_STATES = [...chatPrivateRoute, ...chatPrivatePopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ChatPrivateComponent,
    ChatPrivateDetailComponent,
    ChatPrivateUpdateComponent,
    ChatPrivateDeleteDialogComponent,
    ChatPrivateDeletePopupComponent
  ],
  entryComponents: [ChatPrivateComponent, ChatPrivateUpdateComponent, ChatPrivateDeleteDialogComponent, ChatPrivateDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosChatPrivateModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
