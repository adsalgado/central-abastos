import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IOfertaProveedor, OfertaProveedor } from 'app/shared/model/oferta-proveedor.model';
import { OfertaProveedorService } from './oferta-proveedor.service';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { IEstatus } from 'app/shared/model/estatus.model';
import { EstatusService } from 'app/entities/estatus';
import { ITipoOferta } from 'app/shared/model/tipo-oferta.model';
import { TipoOfertaService } from 'app/entities/tipo-oferta';

@Component({
  selector: 'jhi-oferta-proveedor-update',
  templateUrl: './oferta-proveedor-update.component.html'
})
export class OfertaProveedorUpdateComponent implements OnInit {
  isSaving: boolean;

  proveedors: IProveedor[];

  productos: IProducto[];

  estatuses: IEstatus[];

  tipoofertas: ITipoOferta[];
  fechaInicioDp: any;
  fechaFinDp: any;

  editForm = this.fb.group({
    id: [],
    fechaInicio: [],
    fechaFin: [],
    proveedorId: [],
    productoId: [],
    estatusId: [],
    tipoOfertaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ofertaProveedorService: OfertaProveedorService,
    protected proveedorService: ProveedorService,
    protected productoService: ProductoService,
    protected estatusService: EstatusService,
    protected tipoOfertaService: TipoOfertaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ ofertaProveedor }) => {
      this.updateForm(ofertaProveedor);
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
    this.tipoOfertaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipoOferta[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipoOferta[]>) => response.body)
      )
      .subscribe((res: ITipoOferta[]) => (this.tipoofertas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(ofertaProveedor: IOfertaProveedor) {
    this.editForm.patchValue({
      id: ofertaProveedor.id,
      fechaInicio: ofertaProveedor.fechaInicio,
      fechaFin: ofertaProveedor.fechaFin,
      proveedorId: ofertaProveedor.proveedorId,
      productoId: ofertaProveedor.productoId,
      estatusId: ofertaProveedor.estatusId,
      tipoOfertaId: ofertaProveedor.tipoOfertaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const ofertaProveedor = this.createFromForm();
    if (ofertaProveedor.id !== undefined) {
      this.subscribeToSaveResponse(this.ofertaProveedorService.update(ofertaProveedor));
    } else {
      this.subscribeToSaveResponse(this.ofertaProveedorService.create(ofertaProveedor));
    }
  }

  private createFromForm(): IOfertaProveedor {
    return {
      ...new OfertaProveedor(),
      id: this.editForm.get(['id']).value,
      fechaInicio: this.editForm.get(['fechaInicio']).value,
      fechaFin: this.editForm.get(['fechaFin']).value,
      proveedorId: this.editForm.get(['proveedorId']).value,
      productoId: this.editForm.get(['productoId']).value,
      estatusId: this.editForm.get(['estatusId']).value,
      tipoOfertaId: this.editForm.get(['tipoOfertaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOfertaProveedor>>) {
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

  trackTipoOfertaById(index: number, item: ITipoOferta) {
    return item.id;
  }
}
