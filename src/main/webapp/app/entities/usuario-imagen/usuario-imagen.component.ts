import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUsuarioImagen } from 'app/shared/model/usuario-imagen.model';
import { AccountService } from 'app/core';
import { UsuarioImagenService } from './usuario-imagen.service';

@Component({
  selector: 'jhi-usuario-imagen',
  templateUrl: './usuario-imagen.component.html'
})
export class UsuarioImagenComponent implements OnInit, OnDestroy {
  usuarioImagens: IUsuarioImagen[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected usuarioImagenService: UsuarioImagenService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.usuarioImagenService
      .query()
      .pipe(
        filter((res: HttpResponse<IUsuarioImagen[]>) => res.ok),
        map((res: HttpResponse<IUsuarioImagen[]>) => res.body)
      )
      .subscribe(
        (res: IUsuarioImagen[]) => {
          this.usuarioImagens = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInUsuarioImagens();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUsuarioImagen) {
    return item.id;
  }

  registerChangeInUsuarioImagens() {
    this.eventSubscriber = this.eventManager.subscribe('usuarioImagenListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
