import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITipoOferta } from 'app/shared/model/tipo-oferta.model';
import { AccountService } from 'app/core';
import { TipoOfertaService } from './tipo-oferta.service';

@Component({
  selector: 'jhi-tipo-oferta',
  templateUrl: './tipo-oferta.component.html'
})
export class TipoOfertaComponent implements OnInit, OnDestroy {
  tipoOfertas: ITipoOferta[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected tipoOfertaService: TipoOfertaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.tipoOfertaService
      .query()
      .pipe(
        filter((res: HttpResponse<ITipoOferta[]>) => res.ok),
        map((res: HttpResponse<ITipoOferta[]>) => res.body)
      )
      .subscribe(
        (res: ITipoOferta[]) => {
          this.tipoOfertas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTipoOfertas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITipoOferta) {
    return item.id;
  }

  registerChangeInTipoOfertas() {
    this.eventSubscriber = this.eventManager.subscribe('tipoOfertaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
