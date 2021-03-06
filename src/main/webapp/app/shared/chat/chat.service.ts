import { Location } from '@angular/common';
import { Injectable } from '@angular/core';
import { AuthServerProvider } from 'app/core/auth/auth-jwt.service';
import { Observable, Observer, Subscription } from 'rxjs';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'webstomp-client';

@Injectable()
export class ChatService {
  stompClient = null;
  subscriber = null;
  connection: Promise<any>;
  connectedPromise: any;
  listener: Observable<any>;
  listenerObserver: Observer<any>;
  alreadyConnectedOnce = false;
  private subscription: Subscription;

  constructor(private authServerProvider: AuthServerProvider, private location: Location) {
    this.connection = this.createConnection();
    this.listener = this.createListener();
  }

  connect() {
    if (this.connectedPromise === null) {
      this.connection = this.createConnection();
    }
    // building absolute path so that websocket doesn't fail when deploying with a context path
    let url = '/websocket/chat';
    url = this.location.prepareExternalUrl(url);
    const authToken = this.authServerProvider.getToken();
    console.log('authToken' + authToken);

    if (authToken) {
      url += '?access_token=' + authToken;
    }
    console.log('---------------------------------------------');
    console.log(url);

    const socket = new SockJS(url);
    this.stompClient = Stomp.over(socket);
    const headers = {};
    this.stompClient.connect(headers, () => {
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

  sendMessage(message) {
    if (this.stompClient !== null && this.stompClient.connected) {
      this.stompClient.send(
        '/chat', // destination
        JSON.stringify({ message }), // body
        {} // header
      );
    }
  }

  subscribe() {
    this.connection.then(() => {
      this.subscriber = this.stompClient.subscribe('/chat/public', data => {
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
