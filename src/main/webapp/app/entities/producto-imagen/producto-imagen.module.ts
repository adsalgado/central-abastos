import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import {
  ProductoImagenComponent,
  ProductoImagenDetailComponent,
  ProductoImagenUpdateComponent,
  ProductoImagenDeletePopupComponent,
  ProductoImagenDeleteDialogComponent,
  productoImagenRoute,
  productoImagenPopupRoute
} from './';

const ENTITY_STATES = [...productoImagenRoute, ...productoImagenPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductoImagenComponent,
    ProductoImagenDetailComponent,
    ProductoImagenUpdateComponent,
    ProductoImagenDeleteDialogComponent,
    ProductoImagenDeletePopupComponent
  ],
  entryComponents: [
    ProductoImagenComponent,
    ProductoImagenUpdateComponent,
    ProductoImagenDeleteDialogComponent,
    ProductoImagenDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosProductoImagenModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
