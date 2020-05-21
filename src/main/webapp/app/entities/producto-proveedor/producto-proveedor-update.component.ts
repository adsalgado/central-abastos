import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IProductoProveedor, ProductoProveedor } from 'app/shared/model/producto-proveedor.model';
import { ProductoProveedorService } from './producto-proveedor.service';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { IEstatus } from 'app/shared/model/estatus.model';
import { EstatusService } from 'app/entities/estatus';

@Component({
  selector: 'jhi-producto-proveedor-update',
  templateUrl: './producto-proveedor-update.component.html'
})
export class ProductoProveedorUpdateComponent implements OnInit {
  isSaving: boolean;

  proveedors: IProveedor[];

  productos: IProducto[];

  estatuses: IEstatus[];

  editForm = this.fb.group({
    id: [],
    precioSinIva: [null, [Validators.required]],
    precio: [null, [Validators.required]],
    fechaAlta: [],
    fechaModificacion: [],
    fechaOtra: [],
    proveedorId: [],
    productoId: [],
    estatusId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productoProveedorService: ProductoProveedorService,
    protected proveedorService: ProveedorService,
    protected productoService: ProductoService,
    protected estatusService: EstatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productoProveedor }) => {
      this.updateForm(productoProveedor);
    });
    this.proveedorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProveedor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProveedor[]>) => response.body)
      )
      .subscribe((res: IProveedor[]) => (this.proveedors = res), (res: HttpErrorResponse) => this.onError(res.message));
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

  updateForm(productoProveedor: IProductoProveedor) {
    this.editForm.patchValue({
      id: productoProveedor.id,
      precioSinIva: productoProveedor.precioSinIva,
      precio: productoProveedor.precio,
      fechaAlta: productoProveedor.fechaAlta != null ? productoProveedor.fechaAlta.format(DATE_TIME_FORMAT) : null,
      fechaModificacion: productoProveedor.fechaModificacion != null ? productoProveedor.fechaModificacion.format(DATE_TIME_FORMAT) : null,
      proveedorId: productoProveedor.proveedorId,
      productoId: productoProveedor.productoId,
      estatusId: productoProveedor.estatusId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productoProveedor = this.createFromForm();
    if (productoProveedor.id !== undefined) {
      this.subscribeToSaveResponse(this.productoProveedorService.update(productoProveedor));
    } else {
      this.subscribeToSaveResponse(this.productoProveedorService.create(productoProveedor));
    }
  }

  private createFromForm(): IProductoProveedor {
    return {
      ...new ProductoProveedor(),
      id: this.editForm.get(['id']).value,
      precioSinIva: this.editForm.get(['precioSinIva']).value,
      precio: this.editForm.get(['precio']).value,
      fechaAlta:
        this.editForm.get(['fechaAlta']).value != null ? moment(this.editForm.get(['fechaAlta']).value, DATE_TIME_FORMAT) : undefined,
      fechaModificacion:
        this.editForm.get(['fechaModificacion']).value != null
          ? moment(this.editForm.get(['fechaModificacion']).value, DATE_TIME_FORMAT)
          : undefined,
      proveedorId: this.editForm.get(['proveedorId']).value,
      productoId: this.editForm.get(['productoId']).value,
      estatusId: this.editForm.get(['estatusId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductoProveedor>>) {
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

  trackProveedorById(index: number, item: IProveedor) {
    return item.id;
  }

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }

  trackEstatusById(index: number, item: IEstatus) {
    return item.id;
  }
}
