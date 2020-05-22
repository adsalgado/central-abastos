import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AbastosSharedModule } from 'app/shared';

import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import {
  ProductoProveedorComponent,
  ProductoProveedorDetailComponent,
  ProductoProveedorUpdateComponent,
  ProductoProveedorDeletePopupComponent,
  ProductoProveedorDeleteDialogComponent,
  productoProveedorRoute,
  productoProveedorPopupRoute
} from './';

const ENTITY_STATES = [...productoProveedorRoute, ...productoProveedorPopupRoute];

@NgModule({
  imports: [AbastosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductoProveedorComponent,
    ProductoProveedorDetailComponent,
    ProductoProveedorUpdateComponent,
    ProductoProveedorDeleteDialogComponent,
    ProductoProveedorDeletePopupComponent
  ],
  entryComponents: [
    ProductoProveedorComponent,
    ProductoProveedorUpdateComponent,
    ProductoProveedorDeleteDialogComponent,
    ProductoProveedorDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosProductoProveedorModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
