import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPagos } from 'app/shared/model/pagos.model';
import { AccountService } from 'app/core';
import { PagosService } from './pagos.service';

@Component({
  selector: 'jhi-pagos',
  templateUrl: './pagos.component.html'
})
export class PagosComponent implements OnInit, OnDestroy {
  pagos: IPagos[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pagosService: PagosService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pagosService
      .query()
      .pipe(
        filter((res: HttpResponse<IPagos[]>) => res.ok),
        map((res: HttpResponse<IPagos[]>) => res.body)
      )
      .subscribe(
        (res: IPagos[]) => {
          this.pagos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPagos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPagos) {
    return item.id;
  }

  registerChangeInPagos() {
    this.eventSubscriber = this.eventManager.subscribe('pagosListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
