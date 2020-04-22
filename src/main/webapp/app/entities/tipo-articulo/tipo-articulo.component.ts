import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITipoArticulo } from 'app/shared/model/tipo-articulo.model';
import { AccountService } from 'app/core';
import { TipoArticuloService } from './tipo-articulo.service';

@Component({
  selector: 'jhi-tipo-articulo',
  templateUrl: './tipo-articulo.component.html'
})
export class TipoArticuloComponent implements OnInit, OnDestroy {
  tipoArticulos: ITipoArticulo[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected tipoArticuloService: TipoArticuloService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.tipoArticuloService
      .query()
      .pipe(
        filter((res: HttpResponse<ITipoArticulo[]>) => res.ok),
        map((res: HttpResponse<ITipoArticulo[]>) => res.body)
      )
      .subscribe(
        (res: ITipoArticulo[]) => {
          this.tipoArticulos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTipoArticulos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITipoArticulo) {
    return item.id;
  }

  registerChangeInTipoArticulos() {
    this.eventSubscriber = this.eventManager.subscribe('tipoArticuloListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
