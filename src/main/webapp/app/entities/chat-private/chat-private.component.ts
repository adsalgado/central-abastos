import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IChatPrivate } from 'app/shared/model/chat-private.model';
import { AccountService } from 'app/core';
import { ChatPrivateService } from './chat-private.service';

@Component({
  selector: 'jhi-chat-private',
  templateUrl: './chat-private.component.html'
})
export class ChatPrivateComponent implements OnInit, OnDestroy {
  chatPrivates: IChatPrivate[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected chatPrivateService: ChatPrivateService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.chatPrivateService
      .query()
      .pipe(
        filter((res: HttpResponse<IChatPrivate[]>) => res.ok),
        map((res: HttpResponse<IChatPrivate[]>) => res.body)
      )
      .subscribe(
        (res: IChatPrivate[]) => {
          this.chatPrivates = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInChatPrivates();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IChatPrivate) {
    return item.id;
  }

  registerChangeInChatPrivates() {
    this.eventSubscriber = this.eventManager.subscribe('chatPrivateListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
