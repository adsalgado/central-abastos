import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOfertaProveedor } from 'app/shared/model/oferta-proveedor.model';
import { AccountService } from 'app/core';
import { OfertaProveedorService } from './oferta-proveedor.service';

@Component({
  selector: 'jhi-oferta-proveedor',
  templateUrl: './oferta-proveedor.component.html'
})
export class OfertaProveedorComponent implements OnInit, OnDestroy {
  ofertaProveedors: IOfertaProveedor[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected ofertaProveedorService: OfertaProveedorService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.ofertaProveedorService
      .query()
      .pipe(
        filter((res: HttpResponse<IOfertaProveedor[]>) => res.ok),
        map((res: HttpResponse<IOfertaProveedor[]>) => res.body)
      )
      .subscribe(
        (res: IOfertaProveedor[]) => {
          this.ofertaProveedors = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInOfertaProveedors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IOfertaProveedor) {
    return item.id;
  }

  registerChangeInOfertaProveedors() {
    this.eventSubscriber = this.eventManager.subscribe('ofertaProveedorListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
