import { AbastosAdminModule } from './admin/admin.module';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { NgbActiveModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
//import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TableModule } from 'primeng/table';
import { CalendarModule } from 'primeng/calendar';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';

import { AgmCoreModule } from '@agm/core';
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import { MaterialModule } from './material.module';
import { SlickCarouselModule } from 'ngx-slick-carousel';
import { AbastosAppRoutingModule } from './app-routing.module';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { NgJhipsterModule } from 'ng-jhipster';
import { AbastosSharedModule } from './shared';
import { AbastosCoreModule } from './core';
import { AbastosHomeModule } from './home';
import { AbastosAccountModule } from './account/account.module';
import { AbastosEntityModule } from './entities/entity.module';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { CookieModule } from 'ngx-cookie';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { AppComponent } from './app.component';
import { IonicModule } from 'ionic-angular';
import { environment } from '../environments/environment.prod';
// For MDB Angular Free
import { AngularFireMessagingModule } from '@angular/fire/messaging';
import { AngularFireDatabaseModule } from '@angular/fire/database';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { AngularFireModule, FirebaseOptionsToken, FirebaseNameOrConfigToken } from '@angular/fire';
@NgModule({
  imports: [
    AbastosAppRoutingModule,
    BrowserModule,
    NgxWebstorageModule.forRoot(),
    NgJhipsterModule.forRoot({
      // set below to true to make alerts look like toast
      alertAsToast: false,
      alertTimeout: 5000,
      i18nEnabled: true,
      defaultI18nLang: 'es'
    }),
    AbastosSharedModule.forRoot(),
    AbastosCoreModule,
    AbastosHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    AbastosEntityModule,
    AbastosAdminModule,
    ReactiveFormsModule,
    MaterialModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    NgbModule,
    FormsModule,

    //NgbActiveModal,
    TableModule,
    CalendarModule,
    InputTextModule,
    ButtonModule,

    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyBlV4YYNLa5dk1zKdfviEGFKRrRg3Nbnxo'
    }),
    CKEditorModule,
    //SlickCarouselModule,
    SlickCarouselModule
  ],

  exports: [
    BrowserModule,
    NgxWebstorageModule,
    NgJhipsterModule,
    AbastosSharedModule,
    AbastosCoreModule,
    AbastosHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    AbastosEntityModule,
    AbastosAppRoutingModule,
    AbastosAdminModule,
    ReactiveFormsModule,
    MaterialModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    NgbModule,
    FormsModule,
    //NgbActiveModal
    TableModule,
    CalendarModule,
    InputTextModule,
    ButtonModule,

    AgmCoreModule,
    CKEditorModule,
    //SlickCarouselModule,
    SlickCarouselModule
  ]
})
export class ViewModule {}
