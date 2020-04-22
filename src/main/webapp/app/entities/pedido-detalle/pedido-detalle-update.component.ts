import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPedidoDetalle, PedidoDetalle } from 'app/shared/model/pedido-detalle.model';
import { PedidoDetalleService } from './pedido-detalle.service';
import { IPedido } from 'app/shared/model/pedido.model';
import { PedidoService } from 'app/entities/pedido';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { IEstatus } from 'app/shared/model/estatus.model';
import { EstatusService } from 'app/entities/estatus';

@Component({
  selector: 'jhi-pedido-detalle-update',
  templateUrl: './pedido-detalle-update.component.html'
})
export class PedidoDetalleUpdateComponent implements OnInit {
  isSaving: boolean;

  pedidos: IPedido[];

  productos: IProducto[];

  estatuses: IEstatus[];

  editForm = this.fb.group({
    id: [],
    cantidad: [],
    totalSinIva: [],
    total: [],
    pedidoId: [],
    productoId: [],
    estatusId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pedidoDetalleService: PedidoDetalleService,
    protected pedidoService: PedidoService,
    protected productoService: ProductoService,
    protected estatusService: EstatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pedidoDetalle }) => {
      this.updateForm(pedidoDetalle);
    });
    this.pedidoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPedido[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPedido[]>) => response.body)
      )
      .subscribe((res: IPedido[]) => (this.pedidos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.productoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProducto[]>) => response.body)
      )
      .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.estatusService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstatus[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstatus[]>) => response.body)
      )
      .subscribe((res: IEstatus[]) => (this.estatuses = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(pedidoDetalle: IPedidoDetalle) {
    this.editForm.patchValue({
      id: pedidoDetalle.id,
      cantidad: pedidoDetalle.cantidad,
      totalSinIva: pedidoDetalle.totalSinIva,
      total: pedidoDetalle.total,
      pedidoId: pedidoDetalle.pedidoId,
      productoId: pedidoDetalle.productoId,
      estatusId: pedidoDetalle.estatusId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pedidoDetalle = this.createFromForm();
    if (pedidoDetalle.id !== undefined) {
      this.subscribeToSaveResponse(this.pedidoDetalleService.update(pedidoDetalle));
    } else {
      this.subscribeToSaveResponse(this.pedidoDetalleService.create(pedidoDetalle));
    }
  }

  private createFromForm(): IPedidoDetalle {
    return {
      ...new PedidoDetalle(),
      id: this.editForm.get(['id']).value,
      cantidad: this.editForm.get(['cantidad']).value,
      totalSinIva: this.editForm.get(['totalSinIva']).value,
      total: this.editForm.get(['total']).value,
      pedidoId: this.editForm.get(['pedidoId']).value,
      productoId: this.editForm.get(['productoId']).value,
      estatusId: this.editForm.get(['estatusId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPedidoDetalle>>) {
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

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }

  trackEstatusById(index: number, item: IEstatus) {
    return item.id;
  }
}
