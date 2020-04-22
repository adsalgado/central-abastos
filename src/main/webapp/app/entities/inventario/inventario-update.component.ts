import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IInventario, Inventario } from 'app/shared/model/inventario.model';
import { InventarioService } from './inventario.service';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';

@Component({
  selector: 'jhi-inventario-update',
  templateUrl: './inventario-update.component.html'
})
export class InventarioUpdateComponent implements OnInit {
  isSaving: boolean;

  proveedors: IProveedor[];

  productos: IProducto[];

  editForm = this.fb.group({
    id: [],
    total: [null, [Validators.required]],
    proveedorId: [],
    productoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected inventarioService: InventarioService,
    protected proveedorService: ProveedorService,
    protected productoService: ProductoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ inventario }) => {
      this.updateForm(inventario);
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
  }

  updateForm(inventario: IInventario) {
    this.editForm.patchValue({
      id: inventario.id,
      total: inventario.total,
      proveedorId: inventario.proveedorId,
      productoId: inventario.productoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const inventario = this.createFromForm();
    if (inventario.id !== undefined) {
      this.subscribeToSaveResponse(this.inventarioService.update(inventario));
    } else {
      this.subscribeToSaveResponse(this.inventarioService.create(inventario));
    }
  }

  private createFromForm(): IInventario {
    return {
      ...new Inventario(),
      id: this.editForm.get(['id']).value,
      total: this.editForm.get(['total']).value,
      proveedorId: this.editForm.get(['proveedorId']).value,
      productoId: this.editForm.get(['productoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventario>>) {
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
}
