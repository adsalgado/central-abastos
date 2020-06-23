import { Injectable, NgZone } from '@angular/core';
import { AngularFireMessaging } from '@angular/fire/messaging';
import { BehaviorSubject, Subject } from 'rxjs';
import { Queja } from 'app/shared/model/queja.model';
@Injectable()
export class MessagingService {
  currentMessage = new Subject<any>();

  constructor(private angularFireMessaging: AngularFireMessaging, private zone: NgZone) {}

  requestPermission() {
    this.angularFireMessaging.requestToken.subscribe(
      token => {
        console.log('NOTIFICATIONS TOKEN ===> ' + token);
        //TODO: Save Messaging Token here!
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
