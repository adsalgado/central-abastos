import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPedidoDetalle } from 'app/shared/model/pedido-detalle.model';
import { AccountService } from 'app/core';
import { PedidoDetalleService } from './pedido-detalle.service';

@Component({
  selector: 'jhi-pedido-detalle',
  templateUrl: './pedido-detalle.component.html'
})
export class PedidoDetalleComponent implements OnInit, OnDestroy {
  pedidoDetalles: IPedidoDetalle[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pedidoDetalleService: PedidoDetalleService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pedidoDetalleService
      .query()
      .pipe(
        filter((res: HttpResponse<IPedidoDetalle[]>) => res.ok),
        map((res: HttpResponse<IPedidoDetalle[]>) => res.body)
      )
      .subscribe(
        (res: IPedidoDetalle[]) => {
          this.pedidoDetalles = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPedidoDetalles();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPedidoDetalle) {
    return item.id;
  }

  registerChangeInPedidoDetalles() {
    this.eventSubscriber = this.eventManager.subscribe('pedidoDetalleListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
