import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IInventario } from 'app/shared/model/inventario.model';
import { AccountService } from 'app/core';
import { InventarioService } from './inventario.service';

@Component({
  selector: 'jhi-inventario',
  templateUrl: './inventario.component.html'
})
export class InventarioComponent implements OnInit, OnDestroy {
  inventarios: IInventario[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected inventarioService: InventarioService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.inventarioService
      .query()
      .pipe(
        filter((res: HttpResponse<IInventario[]>) => res.ok),
        map((res: HttpResponse<IInventario[]>) => res.body)
      )
      .subscribe(
        (res: IInventario[]) => {
          this.inventarios = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInInventarios();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInventario) {
    return item.id;
  }

  registerChangeInInventarios() {
    this.eventSubscriber = this.eventManager.subscribe('inventarioListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
