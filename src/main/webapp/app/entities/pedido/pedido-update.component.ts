import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPedido, Pedido } from 'app/shared/model/pedido.model';
import { PedidoService } from './pedido.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';
import { IEstatus } from 'app/shared/model/estatus.model';
import { EstatusService } from 'app/entities/estatus';
import { ITransportista } from 'app/shared/model/transportista.model';
import { TransportistaService } from 'app/entities/transportista';
import { IRecolector } from 'app/shared/model/recolector.model';
import { RecolectorService } from 'app/entities/recolector';

@Component({
  selector: 'jhi-pedido-update',
  templateUrl: './pedido-update.component.html'
})
export class PedidoUpdateComponent implements OnInit {
  isSaving: boolean;

  clientes: ICliente[];

  estatuses: IEstatus[];

  transportistas: ITransportista[];

  recolectors: IRecolector[];
  fechaPedidoDp: any;
  fechaPreparacionDp: any;
  fechaCobroDp: any;
  fechaEntregaDp: any;

  editForm = this.fb.group({
    id: [],
    totalSinIva: [],
    comisionTransportista: [],
    comisionPreparador: [],
    total: [],
    fechaPedido: [],
    fechaPreparacion: [],
    fechaCobro: [],
    fechaEntrega: [],
    clienteId: [],
    estatusId: [],
    transportistaId: [],
    recolectorId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pedidoService: PedidoService,
    protected clienteService: ClienteService,
    protected estatusService: EstatusService,
    protected transportistaService: TransportistaService,
    protected recolectorService: RecolectorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pedido }) => {
      this.updateForm(pedido);
    });
    this.clienteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICliente[]>) => response.body)
      )
      .subscribe((res: ICliente[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.estatusService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstatus[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstatus[]>) => response.body)
      )
      .subscribe((res: IEstatus[]) => (this.estatuses = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.transportistaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITransportista[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITransportista[]>) => response.body)
      )
      .subscribe((res: ITransportista[]) => (this.transportistas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.recolectorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRecolector[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRecolector[]>) => response.body)
      )
      .subscribe((res: IRecolector[]) => (this.recolectors = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(pedido: IPedido) {
    this.editForm.patchValue({
      id: pedido.id,
      totalSinIva: pedido.totalSinIva,
      comisionTransportista: pedido.comisionTransportista,
      comisionPreparador: pedido.comisionPreparador,
      total: pedido.total,
      fechaPedido: pedido.fechaPedido,
      fechaPreparacion: pedido.fechaPreparacion,
      fechaCobro: pedido.fechaCobro,
      fechaEntrega: pedido.fechaEntrega,
      clienteId: pedido.clienteId,
      estatusId: pedido.estatusId,
      transportistaId: pedido.transportistaId,
      recolectorId: pedido.recolectorId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pedido = this.createFromForm();
    if (pedido.id !== undefined) {
      this.subscribeToSaveResponse(this.pedidoService.update(pedido));
    } else {
      this.subscribeToSaveResponse(this.pedidoService.create(pedido));
    }
  }

  private createFromForm(): IPedido {
    return {
      ...new Pedido(),
      id: this.editForm.get(['id']).value,
      totalSinIva: this.editForm.get(['totalSinIva']).value,
      comisionTransportista: this.editForm.get(['comisionTransportista']).value,
      comisionPreparador: this.editForm.get(['comisionPreparador']).value,
      total: this.editForm.get(['total']).value,
      fechaPedido: this.editForm.get(['fechaPedido']).value,
      fechaPreparacion: this.editForm.get(['fechaPreparacion']).value,
      fechaCobro: this.editForm.get(['fechaCobro']).value,
      fechaEntrega: this.editForm.get(['fechaEntrega']).value,
      clienteId: this.editForm.get(['clienteId']).value,
      estatusId: this.editForm.get(['estatusId']).value,
      transportistaId: this.editForm.get(['transportistaId']).value,
      recolectorId: this.editForm.get(['recolectorId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPedido>>) {
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

  trackClienteById(index: number, item: ICliente) {
    return item.id;
  }

  trackEstatusById(index: number, item: IEstatus) {
    return item.id;
  }

  trackTransportistaById(index: number, item: ITransportista) {
    return item.id;
  }

  trackRecolectorById(index: number, item: IRecolector) {
    return item.id;
  }
}
