import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IHistoricoPedido, HistoricoPedido } from 'app/shared/model/historico-pedido.model';
import { HistoricoPedidoService } from './historico-pedido.service';
import { IPedido } from 'app/shared/model/pedido.model';
import { PedidoService } from 'app/entities/pedido';

@Component({
  selector: 'jhi-historico-pedido-update',
  templateUrl: './historico-pedido-update.component.html'
})
export class HistoricoPedidoUpdateComponent implements OnInit {
  isSaving: boolean;

  pedidos: IPedido[];
  fechaEstatusDp: any;

  editForm = this.fb.group({
    id: [],
    fechaEstatus: [],
    pedidoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected historicoPedidoService: HistoricoPedidoService,
    protected pedidoService: PedidoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ historicoPedido }) => {
      this.updateForm(historicoPedido);
    });
    this.pedidoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPedido[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPedido[]>) => response.body)
      )
      .subscribe((res: IPedido[]) => (this.pedidos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(historicoPedido: IHistoricoPedido) {
    this.editForm.patchValue({
      id: historicoPedido.id,
      fechaEstatus: historicoPedido.fechaEstatus,
      pedidoId: historicoPedido.pedidoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const historicoPedido = this.createFromForm();
    if (historicoPedido.id !== undefined) {
      this.subscribeToSaveResponse(this.historicoPedidoService.update(historicoPedido));
    } else {
      this.subscribeToSaveResponse(this.historicoPedidoService.create(historicoPedido));
    }
  }

  private createFromForm(): IHistoricoPedido {
    return {
      ...new HistoricoPedido(),
      id: this.editForm.get(['id']).value,
      fechaEstatus: this.editForm.get(['fechaEstatus']).value,
      pedidoId: this.editForm.get(['pedidoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHistoricoPedido>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackPedidoById(index: number, item: IPedido) {
    return item.id;
  }
}
