import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IInventarioHistorico } from 'app/shared/model/inventario-historico.model';
import { AccountService } from 'app/core';
import { InventarioHistoricoService } from './inventario-historico.service';

@Component({
  selector: 'jhi-inventario-historico',
  templateUrl: './inventario-historico.component.html'
})
export class InventarioHistoricoComponent implements OnInit, OnDestroy {
  inventarioHistoricos: IInventarioHistorico[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected inventarioHistoricoService: InventarioHistoricoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.inventarioHistoricoService
      .query()
      .pipe(
        filter((res: HttpResponse<IInventarioHistorico[]>) => res.ok),
        map((res: HttpResponse<IInventarioHistorico[]>) => res.body)
      )
      .subscribe(
        (res: IInventarioHistorico[]) => {
          this.inventarioHistoricos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInInventarioHistoricos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInventarioHistorico) {
    return item.id;
  }

  registerChangeInInventarioHistoricos() {
    this.eventSubscriber = this.eventManager.subscribe('inventarioHistoricoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
