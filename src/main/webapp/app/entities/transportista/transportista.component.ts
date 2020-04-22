import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITransportista } from 'app/shared/model/transportista.model';
import { AccountService } from 'app/core';
import { TransportistaService } from './transportista.service';

@Component({
  selector: 'jhi-transportista',
  templateUrl: './transportista.component.html'
})
export class TransportistaComponent implements OnInit, OnDestroy {
  transportistas: ITransportista[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected transportistaService: TransportistaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.transportistaService
      .query()
      .pipe(
        filter((res: HttpResponse<ITransportista[]>) => res.ok),
        map((res: HttpResponse<ITransportista[]>) => res.body)
      )
      .subscribe(
        (res: ITransportista[]) => {
          this.transportistas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTransportistas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITransportista) {
    return item.id;
  }

  registerChangeInTransportistas() {
    this.eventSubscriber = this.eventManager.subscribe('transportistaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
