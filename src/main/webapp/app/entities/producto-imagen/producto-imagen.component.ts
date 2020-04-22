import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProductoImagen } from 'app/shared/model/producto-imagen.model';
import { AccountService } from 'app/core';
import { ProductoImagenService } from './producto-imagen.service';

@Component({
  selector: 'jhi-producto-imagen',
  templateUrl: './producto-imagen.component.html'
})
export class ProductoImagenComponent implements OnInit, OnDestroy {
  productoImagens: IProductoImagen[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected productoImagenService: ProductoImagenService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.productoImagenService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductoImagen[]>) => res.ok),
        map((res: HttpResponse<IProductoImagen[]>) => res.body)
      )
      .subscribe(
        (res: IProductoImagen[]) => {
          this.productoImagens = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProductoImagens();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductoImagen) {
    return item.id;
  }

  registerChangeInProductoImagens() {
    this.eventSubscriber = this.eventManager.subscribe('productoImagenListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
