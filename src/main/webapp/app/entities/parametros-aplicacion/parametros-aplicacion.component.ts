import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IParametrosAplicacion } from 'app/shared/model/parametros-aplicacion.model';
import { AccountService } from 'app/core';
import { ParametrosAplicacionService } from './parametros-aplicacion.service';

@Component({
  selector: 'jhi-parametros-aplicacion',
  templateUrl: './parametros-aplicacion.component.html'
})
export class ParametrosAplicacionComponent implements OnInit, OnDestroy {
  parametrosAplicacions: IParametrosAplicacion[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected parametrosAplicacionService: ParametrosAplicacionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.parametrosAplicacionService
      .query()
      .pipe(
        filter((res: HttpResponse<IParametrosAplicacion[]>) => res.ok),
        map((res: HttpResponse<IParametrosAplicacion[]>) => res.body)
      )
      .subscribe(
        (res: IParametrosAplicacion[]) => {
          this.parametrosAplicacions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInParametrosAplicacions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IParametrosAplicacion) {
    return item.id;
  }

  registerChangeInParametrosAplicacions() {
    this.eventSubscriber = this.eventManager.subscribe('parametrosAplicacionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
