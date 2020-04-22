import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  UsuarioImagenComponent,
  UsuarioImagenDetailComponent,
  UsuarioImagenUpdateComponent,
  UsuarioImagenDeletePopupComponent,
  UsuarioImagenDeleteDialogComponent,
  usuarioImagenRoute,
  usuarioImagenPopupRoute
} from './';

const ENTITY_STATES = [...usuarioImagenRoute, ...usuarioImagenPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UsuarioImagenComponent,
    UsuarioImagenDetailComponent,
    UsuarioImagenUpdateComponent,
    UsuarioImagenDeleteDialogComponent,
    UsuarioImagenDeletePopupComponent
  ],
  entryComponents: [
    UsuarioImagenComponent,
    UsuarioImagenUpdateComponent,
    UsuarioImagenDeleteDialogComponent,
    UsuarioImagenDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosUsuarioImagenModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
