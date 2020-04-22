import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICarritoHistoricoDetalle, CarritoHistoricoDetalle } from 'app/shared/model/carrito-historico-detalle.model';
import { CarritoHistoricoDetalleService } from './carrito-historico-detalle.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { ICarritoHistorico } from 'app/shared/model/carrito-historico.model';
import { CarritoHistoricoService } from 'app/entities/carrito-historico';

@Component({
  selector: 'jhi-carrito-historico-detalle-update',
  templateUrl: './carrito-historico-detalle-update.component.html'
})
export class CarritoHistoricoDetalleUpdateComponent implements OnInit {
  isSaving: boolean;

  productos: IProducto[];

  carritohistoricos: ICarritoHistorico[];

  editForm = this.fb.group({
    id: [],
    cantidad: [null, [Validators.required]],
    precio: [],
    productoId: [],
    carritoHistoricoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected carritoHistoricoDetalleService: CarritoHistoricoDetalleService,
    protected productoService: ProductoService,
    protected carritoHistoricoService: CarritoHistoricoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ carritoHistoricoDetalle }) => {
      this.updateForm(carritoHistoricoDetalle);
    });
    this.productoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProducto[]>) => response.body)
      )
      .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.carritoHistoricoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICarritoHistorico[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICarritoHistorico[]>) => response.body)
      )
      .subscribe((res: ICarritoHistorico[]) => (this.carritohistoricos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(carritoHistoricoDetalle: ICarritoHistoricoDetalle) {
    this.editForm.patchValue({
      id: carritoHistoricoDetalle.id,
      cantidad: carritoHistoricoDetalle.cantidad,
      precio: carritoHistoricoDetalle.precio,
      productoId: carritoHistoricoDetalle.productoId,
      carritoHistoricoId: carritoHistoricoDetalle.carritoHistoricoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const carritoHistoricoDetalle = this.createFromForm();
    if (carritoHistoricoDetalle.id !== undefined) {
      this.subscribeToSaveResponse(this.carritoHistoricoDetalleService.update(carritoHistoricoDetalle));
    } else {
      this.subscribeToSaveResponse(this.carritoHistoricoDetalleService.create(carritoHistoricoDetalle));
    }
  }

  private createFromForm(): ICarritoHistoricoDetalle {
    return {
      ...new CarritoHistoricoDetalle(),
      id: this.editForm.get(['id']).value,
      cantidad: this.editForm.get(['cantidad']).value,
      precio: this.editForm.get(['precio']).value,
      productoId: this.editForm.get(['productoId']).value,
      carritoHistoricoId: this.editForm.get(['carritoHistoricoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarritoHistoricoDetalle>>) {
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

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }

  trackCarritoHistoricoById(index: number, item: ICarritoHistorico) {
    return item.id;
  }
}
