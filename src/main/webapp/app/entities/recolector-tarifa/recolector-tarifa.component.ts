import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRecolectorTarifa } from 'app/shared/model/recolector-tarifa.model';
import { AccountService } from 'app/core';
import { RecolectorTarifaService } from './recolector-tarifa.service';

@Component({
  selector: 'jhi-recolector-tarifa',
  templateUrl: './recolector-tarifa.component.html'
})
export class RecolectorTarifaComponent implements OnInit, OnDestroy {
  recolectorTarifas: IRecolectorTarifa[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected recolectorTarifaService: RecolectorTarifaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.recolectorTarifaService
      .query()
      .pipe(
        filter((res: HttpResponse<IRecolectorTarifa[]>) => res.ok),
        map((res: HttpResponse<IRecolectorTarifa[]>) => res.body)
      )
      .subscribe(
        (res: IRecolectorTarifa[]) => {
          this.recolectorTarifas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRecolectorTarifas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRecolectorTarifa) {
    return item.id;
  }

  registerChangeInRecolectorTarifas() {
    this.eventSubscriber = this.eventManager.subscribe('recolectorTarifaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
