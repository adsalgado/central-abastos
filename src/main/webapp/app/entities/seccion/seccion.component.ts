import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISeccion } from 'app/shared/model/seccion.model';
import { AccountService } from 'app/core';
import { SeccionService } from './seccion.service';
import { environment } from '../../../environments/environment.prod';

@Component({
  selector: 'jhi-seccion',
  templateUrl: './seccion.component.html'
})
export class SeccionComponent implements OnInit, OnDestroy {
  seccions: ISeccion[];
  currentAccount: any;
  eventSubscriber: Subscription;
  public env: any = environment;

  constructor(
    protected seccionService: SeccionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.seccionService
      .query()
      .pipe(
        filter((res: HttpResponse<ISeccion[]>) => res.ok),
        map((res: HttpResponse<ISeccion[]>) => res.body)
      )
      .subscribe(
        (res: ISeccion[]) => {
          this.seccions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSeccions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISeccion) {
    return item.id;
  }

  registerChangeInSeccions() {
    this.eventSubscriber = this.eventManager.subscribe('seccionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
