import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEstatus } from 'app/shared/model/estatus.model';
import { AccountService } from 'app/core';
import { EstatusService } from './estatus.service';

@Component({
  selector: 'jhi-estatus',
  templateUrl: './estatus.component.html'
})
export class EstatusComponent implements OnInit, OnDestroy {
  estatuses: IEstatus[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected estatusService: EstatusService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.estatusService
      .query()
      .pipe(
        filter((res: HttpResponse<IEstatus[]>) => res.ok),
        map((res: HttpResponse<IEstatus[]>) => res.body)
      )
      .subscribe(
        (res: IEstatus[]) => {
          this.estatuses = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInEstatuses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEstatus) {
    return item.id;
  }

  registerChangeInEstatuses() {
    this.eventSubscriber = this.eventManager.subscribe('estatusListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
