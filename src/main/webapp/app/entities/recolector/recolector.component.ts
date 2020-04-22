import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRecolector } from 'app/shared/model/recolector.model';
import { AccountService } from 'app/core';
import { RecolectorService } from './recolector.service';

@Component({
  selector: 'jhi-recolector',
  templateUrl: './recolector.component.html'
})
export class RecolectorComponent implements OnInit, OnDestroy {
  recolectors: IRecolector[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected recolectorService: RecolectorService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.recolectorService
      .query()
      .pipe(
        filter((res: HttpResponse<IRecolector[]>) => res.ok),
        map((res: HttpResponse<IRecolector[]>) => res.body)
      )
      .subscribe(
        (res: IRecolector[]) => {
          this.recolectors = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRecolectors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRecolector) {
    return item.id;
  }

  registerChangeInRecolectors() {
    this.eventSubscriber = this.eventManager.subscribe('recolectorListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
