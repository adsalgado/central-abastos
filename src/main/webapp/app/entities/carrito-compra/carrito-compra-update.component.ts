import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICarritoCompra, CarritoCompra } from 'app/shared/model/carrito-compra.model';
import { CarritoCompraService } from './carrito-compra.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';

@Component({
  selector: 'jhi-carrito-compra-update',
  templateUrl: './carrito-compra-update.component.html'
})
export class CarritoCompraUpdateComponent implements OnInit {
  isSaving: boolean;

  clientes: ICliente[];

  productos: IProducto[];

  editForm = this.fb.group({
    id: [],
    cantidad: [null, [Validators.required]],
    precio: [],
    clienteId: [],
    productoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected carritoCompraService: CarritoCompraService,
    protected clienteService: ClienteService,
    protected productoService: ProductoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ carritoCompra }) => {
      this.updateForm(carritoCompra);
    });
    this.clienteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICliente[]>) => response.body)
      )
      .subscribe((res: ICliente[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.productoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProducto[]>) => response.body)
      )
      .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(carritoCompra: ICarritoCompra) {
    this.editForm.patchValue({
      id: carritoCompra.id,
      cantidad: carritoCompra.cantidad,
      precio: carritoCompra.precio,
      clienteId: carritoCompra.clienteId,
      productoId: carritoCompra.productoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const carritoCompra = this.createFromForm();
    if (carritoCompra.id !== undefined) {
      this.subscribeToSaveResponse(this.carritoCompraService.update(carritoCompra));
    } else {
      this.subscribeToSaveResponse(this.carritoCompraService.create(carritoCompra));
    }
  }

  private createFromForm(): ICarritoCompra {
    return {
      ...new CarritoCompra(),
      id: this.editForm.get(['id']).value,
      cantidad: this.editForm.get(['cantidad']).value,
      precio: this.editForm.get(['precio']).value,
      clienteId: this.editForm.get(['clienteId']).value,
      productoId: this.editForm.get(['productoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarritoCompra>>) {
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

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }
}
