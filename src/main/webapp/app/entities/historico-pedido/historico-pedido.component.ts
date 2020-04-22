import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IHistoricoPedido } from 'app/shared/model/historico-pedido.model';
import { AccountService } from 'app/core';
import { HistoricoPedidoService } from './historico-pedido.service';

@Component({
  selector: 'jhi-historico-pedido',
  templateUrl: './historico-pedido.component.html'
})
export class HistoricoPedidoComponent implements OnInit, OnDestroy {
  historicoPedidos: IHistoricoPedido[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected historicoPedidoService: HistoricoPedidoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.historicoPedidoService
      .query()
      .pipe(
        filter((res: HttpResponse<IHistoricoPedido[]>) => res.ok),
        map((res: HttpResponse<IHistoricoPedido[]>) => res.body)
      )
      .subscribe(
        (res: IHistoricoPedido[]) => {
          this.historicoPedidos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInHistoricoPedidos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IHistoricoPedido) {
    return item.id;
  }

  registerChangeInHistoricoPedidos() {
    this.eventSubscriber = this.eventManager.subscribe('historicoPedidoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
