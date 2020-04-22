import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICarritoCompra } from 'app/shared/model/carrito-compra.model';
import { AccountService } from 'app/core';
import { CarritoCompraService } from './carrito-compra.service';

@Component({
  selector: 'jhi-carrito-compra',
  templateUrl: './carrito-compra.component.html'
})
export class CarritoCompraComponent implements OnInit, OnDestroy {
  carritoCompras: ICarritoCompra[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected carritoCompraService: CarritoCompraService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.carritoCompraService
      .query()
      .pipe(
        filter((res: HttpResponse<ICarritoCompra[]>) => res.ok),
        map((res: HttpResponse<ICarritoCompra[]>) => res.body)
      )
      .subscribe(
        (res: ICarritoCompra[]) => {
          this.carritoCompras = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCarritoCompras();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICarritoCompra) {
    return item.id;
  }

  registerChangeInCarritoCompras() {
    this.eventSubscriber = this.eventManager.subscribe('carritoCompraListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
