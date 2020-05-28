import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProductoProveedor } from 'app/shared/model/producto-proveedor.model';
import { AccountService } from 'app/core';
import { ProductoProveedorService } from './producto-proveedor.service';
import { environment } from '../../../environments/environment.prod';

@Component({
  selector: 'jhi-producto-proveedor',
  templateUrl: './producto-proveedor.component.html'
})
export class ProductoProveedorComponent implements OnInit, OnDestroy {
  productoProveedors: IProductoProveedor[];
  currentAccount: any;
  eventSubscriber: Subscription;
  public env: any = environment;

  constructor(
    protected productoProveedorService: ProductoProveedorService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.productoProveedorService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductoProveedor[]>) => res.ok),
        map((res: HttpResponse<IProductoProveedor[]>) => res.body)
      )
      .subscribe(
        (res: IProductoProveedor[]) => {
          this.productoProveedors = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProductoProveedors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductoProveedor) {
    return item.id;
  }

  registerChangeInProductoProveedors() {
    this.eventSubscriber = this.eventManager.subscribe('productoProveedorListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
