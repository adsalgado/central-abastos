import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AbastosSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [AbastosSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [AbastosSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AbastosSharedModule {
  static forRoot() {
    return {
      ngModule: AbastosSharedModule
    };
  }
}
