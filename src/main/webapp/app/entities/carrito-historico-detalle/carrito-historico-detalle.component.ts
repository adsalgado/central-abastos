import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICarritoHistoricoDetalle } from 'app/shared/model/carrito-historico-detalle.model';
import { AccountService } from 'app/core';
import { CarritoHistoricoDetalleService } from './carrito-historico-detalle.service';

@Component({
  selector: 'jhi-carrito-historico-detalle',
  templateUrl: './carrito-historico-detalle.component.html'
})
export class CarritoHistoricoDetalleComponent implements OnInit, OnDestroy {
  carritoHistoricoDetalles: ICarritoHistoricoDetalle[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected carritoHistoricoDetalleService: CarritoHistoricoDetalleService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.carritoHistoricoDetalleService
      .query()
      .pipe(
        filter((res: HttpResponse<ICarritoHistoricoDetalle[]>) => res.ok),
        map((res: HttpResponse<ICarritoHistoricoDetalle[]>) => res.body)
      )
      .subscribe(
        (res: ICarritoHistoricoDetalle[]) => {
          this.carritoHistoricoDetalles = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCarritoHistoricoDetalles();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICarritoHistoricoDetalle) {
    return item.id;
  }

  registerChangeInCarritoHistoricoDetalles() {
    this.eventSubscriber = this.eventManager.subscribe('carritoHistoricoDetalleListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
