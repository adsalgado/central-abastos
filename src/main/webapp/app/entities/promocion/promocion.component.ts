import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPromocion } from 'app/shared/model/promocion.model';
import { AccountService } from 'app/core';
import { environment } from '../../../environments/environment.prod';
import { PromocionService } from './promocion.service';

@Component({
  selector: 'jhi-promocion',
  templateUrl: './promocion.component.html'
})
export class PromocionComponent implements OnInit, OnDestroy {
  promociones: IPromocion[];
  currentAccount: any;
  eventSubscriber: Subscription;
  public env: any = environment;

  constructor(
    protected promocionService: PromocionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.promocionService
      .query()
      .pipe(
        filter((res: HttpResponse<IPromocion[]>) => res.ok),
        map((res: HttpResponse<IPromocion[]>) => res.body)
      )
      .subscribe(
        (res: IPromocion[]) => {
          this.promociones = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPromocions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPromocion) {
    return item.id;
  }

  registerChangeInPromocions() {
    this.eventSubscriber = this.eventManager.subscribe('promocionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
