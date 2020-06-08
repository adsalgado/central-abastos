import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AbastosSharedModule } from 'app/shared';
import { ReporteCostosComponent, reporteCostosRoute } from './';
import { CalendarModule } from 'primeng/calendar';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { DropdownModule } from 'primeng/dropdown';
import { PanelModule } from 'primeng/panel';
import { ToastModule } from 'primeng/toast';

const ENTITY_STATES = [...reporteCostosRoute];

@NgModule({
  imports: [
    AbastosSharedModule,
    RouterModule.forChild(ENTITY_STATES),
    CalendarModule,
    InputTextModule,
    ButtonModule,
    InputTextareaModule,
    DropdownModule,
    PanelModule,
    ToastModule
  ],
  declarations: [ReporteCostosComponent],
  entryComponents: [ReporteCostosComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReporteCostosModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
