import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITransportistaTarifa } from 'app/shared/model/transportista-tarifa.model';
import { AccountService } from 'app/core';
import { TransportistaTarifaService } from './transportista-tarifa.service';

@Component({
  selector: 'jhi-transportista-tarifa',
  templateUrl: './transportista-tarifa.component.html'
})
export class TransportistaTarifaComponent implements OnInit, OnDestroy {
  transportistaTarifas: ITransportistaTarifa[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected transportistaTarifaService: TransportistaTarifaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.transportistaTarifaService
      .query()
      .pipe(
        filter((res: HttpResponse<ITransportistaTarifa[]>) => res.ok),
        map((res: HttpResponse<ITransportistaTarifa[]>) => res.body)
      )
      .subscribe(
        (res: ITransportistaTarifa[]) => {
          this.transportistaTarifas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTransportistaTarifas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITransportistaTarifa) {
    return item.id;
  }

  registerChangeInTransportistaTarifas() {
    this.eventSubscriber = this.eventManager.subscribe('transportistaTarifaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
