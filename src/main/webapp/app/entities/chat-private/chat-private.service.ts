import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, Observer, Subscription } from 'rxjs';
import { Location } from '@angular/common';

import { CSRFService } from 'app/core/auth/csrf.service';
import { AuthServerProvider } from 'app/core/auth/auth-jwt.service';

import * as SockJS from 'sockjs-client';
import * as Stomp from 'webstomp-client';

@Injectable()
export class ChatPrivateService {
  stompClient = null;
  subscriber = null;
  connection: Promise<any>;
  connectedPromise: any;
  listener: Observable<any>;
  listenerObserver: Observer<any>;
  alreadyConnectedOnce = false;
  private subscription: Subscription;
  sessionId: '';

  constructor(
    private router: Router,
    private authServerProvider: AuthServerProvider,
    private location: Location,
    private csrfService: CSRFService
  ) {
    this.connection = this.createConnection();
    this.listener = this.createListener();
  }

  connect() {
    if (this.connectedPromise === null) {
      this.connection = this.createConnection();
    }
    // building absolute path so that websocket doesn't fail when deploying with a context path
    let url = '/secured/room';
    url = this.location.prepareExternalUrl(url);
    const authToken = this.authServerProvider.getToken();
    console.log('authToken' + authToken);

    if (authToken) {
      url += '?access_token=' + authToken;
    }
    const socket = new SockJS(url);
    this.stompClient = Stomp.over(socket);
    const headers = {};

    this.stompClient.connect(headers, () => {
      let url = this.stompClient.ws._transport.url;
      url = url.substring(url.indexOf('/secured/room/'), url.length);
      url = url.replace('/secured/room/', '');
      url = url.substring(0, url.indexOf('/websocket'));
      url = url.replace(/^[0-9]+\//, '');
      this.sessionId = url;
      this.connectedPromise('success');
      this.connectedPromise = null;
      this.subscribe();
      if (!this.alreadyConnectedOnce) {
        this.alreadyConnectedOnce = true;
      }
    });
  }

  disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
      this.stompClient = null;
    }
    if (this.subscription) {
      this.subscription.unsubscribe();
      this.subscription = null;
    }
    this.alreadyConnectedOnce = false;
  }

  receive() {
    return this.listener;
  }

  sendMessage(fromUser, toUser, message) {
    if (this.stompClient !== null && this.stompClient.connected) {
      this.stompClient.send(
        '/secured/room', // destination
        JSON.stringify({ from: fromUser, to: toUser, text: message, chatId: 16 }), // body
        {} // header
      );
    }
  }

  subscribe() {
    this.connection.then(() => {
      this.subscriber = this.stompClient.subscribe('/secured/user/queue/specific-user' + '-user' + this.sessionId, data => {
        this.listenerObserver.next(JSON.parse(data.body));
      });
    });
  }

  unsubscribe() {
    if (this.subscriber !== null) {
      this.subscriber.unsubscribe();
    }
    this.listener = this.createListener();
  }

  private createListener(): Observable<any> {
    return new Observable(observer => {
      this.listenerObserver = observer;
    });
  }

  private createConnection(): Promise<any> {
    return new Promise((resolve, reject) => (this.connectedPromise = resolve));
  }
}
