import { Injectable, NgZone } from '@angular/core';
import { AngularFireMessaging } from '@angular/fire/messaging';
import { BehaviorSubject, Subject } from 'rxjs';
import { Queja } from 'app/shared/model/queja.model';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient } from '@angular/common/http';
import { LocalStorageEncryptService } from './local-storage-encrypt-service';
import { User } from 'app/models/User';
@Injectable()
export class MessagingService {
  currentMessage = new Subject<any>();

  public resourceUrl = SERVER_API_URL + 'api/usuarios';

  constructor(
    private angularFireMessaging: AngularFireMessaging,
    private zone: NgZone,
    private http: HttpClient,
    private localStorageService: LocalStorageEncryptService
  ) {}

  requestPermission() {
    this.angularFireMessaging.requestToken.subscribe(
      token => {
        const user = this.localStorageService.getFromLocalStorage('userSession');
        user.tokenWeb = token;
        this.http.put<User>(this.resourceUrl, user).subscribe(
          success => {
            console.log('REGRESA DE GUARDAR TOKEN EN BD');
            console.log(success);
          },
          error => {
            console.log(error);
          }
        );
        //Send the token to the server side.
        console.log('TOKEN====> ' + token);
      },
      err => console.error('Unable to get permission to notify.', err)
    );
  }

  receiveMessage() {
    this.angularFireMessaging.messaging.subscribe((messaging: any) => {
      messaging.onMessageCallback = (payload: any) => {
        this.zone.run(() => {
          this.currentMessage.next(payload.data);
        });
      };
    });
  }
}
