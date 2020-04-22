import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IQueja } from 'app/shared/model/queja.model';
import { AccountService } from 'app/core';
import { QuejaService } from './queja.service';

@Component({
  selector: 'jhi-queja',
  templateUrl: './queja.component.html'
})
export class QuejaComponent implements OnInit, OnDestroy {
  quejas: IQueja[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected quejaService: QuejaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.quejaService
      .query()
      .pipe(
        filter((res: HttpResponse<IQueja[]>) => res.ok),
        map((res: HttpResponse<IQueja[]>) => res.body)
      )
      .subscribe(
        (res: IQueja[]) => {
          this.quejas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInQuejas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IQueja) {
    return item.id;
  }

  registerChangeInQuejas() {
    this.eventSubscriber = this.eventManager.subscribe('quejaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
