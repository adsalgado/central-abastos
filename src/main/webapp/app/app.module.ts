import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbDatepickerConfig, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { NgJhipsterModule } from 'ng-jhipster';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { AbastosSharedModule } from 'app/shared';
import { AbastosCoreModule } from 'app/core';
import { AbastosAppRoutingModule } from './app-routing.module';
import { AbastosHomeModule } from './home/home.module';
import { AbastosAccountModule } from './account/account.module';
import { AbastosEntityModule } from './entities/entity.module';
import * as moment from 'moment';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, NavbarComponent, FooterComponentJHI, PageRibbonComponent, ActiveMenuDirective, ErrorComponent } from './layouts';
import { ComponentsModule } from './components.module';
import { ProvidersModule } from './providers.module';
import { AppComponent } from './app.component';
import { RouterModule } from '@angular/router';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { RequestInterceptorService } from './interceptors/request-interceptor.service';
import { AngularFireMessagingModule } from '@angular/fire/messaging';
import { AngularFireDatabaseModule } from '@angular/fire/database';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { AngularFireModule } from '@angular/fire';
import { environment } from '../environments/environment.prod';
import { QRCodeModule } from 'angularx-qrcode';
import { NgxQRCodeModule } from 'ngx-qrcode2';

@NgModule({
  imports: [
    NgbModule,
    ProvidersModule,
    //HttpClientModule,
    ComponentsModule,
    //IonicModule.forRoot(AppComponent),
    /* AngularFireDatabaseModule,
    AngularFireAuthModule,
    AngularFireMessagingModule,
    AngularFireModule.initializeApp(environment.firebase) */
    AngularFireDatabaseModule,
    AngularFireAuthModule,
    AngularFireMessagingModule,
    AngularFireModule.initializeApp(environment.firebase)
  ],

  bootstrap: [AppComponent],

  exports: [
    NgbModule
    //NgbActiveModal
  ]
})
export class AbastosAppModule {
  constructor(private dpConfig: NgbDatepickerConfig) {
    this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
  }
}
