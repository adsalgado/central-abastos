import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICarritoHistorico } from 'app/shared/model/carrito-historico.model';
import { AccountService } from 'app/core';
import { CarritoHistoricoService } from './carrito-historico.service';

@Component({
  selector: 'jhi-carrito-historico',
  templateUrl: './carrito-historico.component.html'
})
export class CarritoHistoricoComponent implements OnInit, OnDestroy {
  carritoHistoricos: ICarritoHistorico[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected carritoHistoricoService: CarritoHistoricoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.carritoHistoricoService
      .query()
      .pipe(
        filter((res: HttpResponse<ICarritoHistorico[]>) => res.ok),
        map((res: HttpResponse<ICarritoHistorico[]>) => res.body)
      )
      .subscribe(
        (res: ICarritoHistorico[]) => {
          this.carritoHistoricos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCarritoHistoricos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICarritoHistorico) {
    return item.id;
  }

  registerChangeInCarritoHistoricos() {
    this.eventSubscriber = this.eventManager.subscribe('carritoHistoricoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
