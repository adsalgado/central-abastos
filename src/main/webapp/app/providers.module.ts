import { Overlay } from '@angular/cdk/overlay';
import { NgModule } from '@angular/core';
import { AlertService } from './services/alert.service';
import { HttpService } from './services/http.model.service';
import { LoadingService } from './services/loading-service';
import { LocalStorageEncryptService } from './services/local-storage-encrypt-service';
import { NavParamsService } from './services/nav-params.service';
import { NavService } from './services/nav.service';
import { ValidationService } from './services/validation.service';
import { AutenticateService } from './services/autenticate.service';
import { Event } from './services/event.service';
import { HttpServiceGeneric } from './services/http.generic.service';

@NgModule({
  providers: [
    ValidationService,
    NavParamsService,
    LocalStorageEncryptService,
    LoadingService,
    Overlay,
    HttpService,
    HttpServiceGeneric,
    AlertService,
    NavService,
    AutenticateService,
    Event
  ]
})
export class ProvidersModule {}
