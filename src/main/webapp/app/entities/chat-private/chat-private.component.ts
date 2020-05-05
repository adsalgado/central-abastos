import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { IChatPrivate } from 'app/shared/model/chat-private.model';
import { AccountService } from 'app/core';
import { ChatPrivateService } from './chat-private.service';
import { LoginModalService } from 'app/core/login/login-modal.service';

import { Account } from 'app/core/user/account.model';

@Component({
  selector: 'jhi-chat-private',
  templateUrl: './chat-private.component.html'
})
export class ChatPrivateComponent implements OnInit, OnDestroy {
  account: Account;
  chatPrivates: IChatPrivate[];
  currentAccount: any;
  eventSubscriber: Subscription;
  messages: Array<Object> = [];

  authSubscription: Subscription;
  modalRef: NgbModalRef;
  message = '';
  userTo = '';

  constructor(
    protected chatPrivateService: ChatPrivateService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected chatService: ChatPrivateService,
    private loginModalService: LoginModalService,
    protected accountService: AccountService
  ) {}

  ngOnInit() {
    this.chatService.connect();

    this.chatService.receive().subscribe(message => {
      console.log(message);
      this.messages.push(message);
    });

    this.accountService.identity().then((account: Account) => {
      this.account = account;
    });
    this.registerAuthenticationSuccess();
    this.registerLogoutSuccess();
  }

  registerAuthenticationSuccess() {
    this.authSubscription = this.eventManager.subscribe('authenticationSuccess', message => {
      this.accountService.identity().then(account => {
        this.account = account;
        this.chatService.disconnect();
        this.chatService.connect();
      });
    });
  }

  registerLogoutSuccess() {
    this.eventManager.subscribe('logoutSuccess', message => {
      this.chatService.disconnect();
      this.chatService.connect();
    });
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  ngOnDestroy() {
    if (this.authSubscription) {
      this.eventManager.destroy(this.authSubscription);
    }
  }

  sendMessage(toUser, message) {
    if (message.length === 0) {
      return;
    }
    this.chatService.sendMessage('admin', toUser, message);
    this.message = '';
  }
}
