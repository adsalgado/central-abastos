import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IProducto, Producto } from 'app/shared/model/producto.model';
import { ProductoService } from './producto.service';
import { IAdjunto } from 'app/shared/model/adjunto.model';
import { AdjuntoService } from 'app/entities/adjunto';
import { IUser, UserService } from 'app/core';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor';
import { ITipoArticulo } from 'app/shared/model/tipo-articulo.model';
import { TipoArticuloService } from 'app/entities/tipo-articulo';
import { ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from 'app/entities/categoria';
import { ISeccion } from 'app/shared/model/seccion.model';
import { SeccionService } from 'app/entities/seccion';
import { IEstatus } from 'app/shared/model/estatus.model';
import { EstatusService } from 'app/entities/estatus';
import { IUnidadMedida } from 'app/shared/model/unidad-medida.model';
import { UnidadMedidaService } from 'app/entities/unidad-medida';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';

@Component({
  selector: 'jhi-producto-update',
  templateUrl: './producto-update.component.html'
})
export class ProductoUpdateComponent implements OnInit {
  isSaving: boolean;

  adjuntos: IAdjunto[];

  users: IUser[];

  proveedors: IProveedor[];

  tipoarticulos: ITipoArticulo[];

  categorias: ICategoria[];

  seccions: ISeccion[];

  estatuses: IEstatus[];

  unidadmedidas: IUnidadMedida[];

  empresas: IEmpresa[];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.maxLength(256)]],
    descripcion: [null, [Validators.required, Validators.maxLength(512)]],
    caracteristicas: [null, [Validators.required, Validators.maxLength(512)]],
    precioSinIva: [null, [Validators.required]],
    precio: [null, [Validators.required]],
    fechaAlta: [],
    fechaModificacion: [],
    adjuntoId: [],
    usuarioAltaId: [],
    usuarioModificacionId: [],
    proveedorId: [],
    tipoArticuloId: [],
    categoriaId: [],
    seccionId: [],
    estatusId: [],
    unidadMedidaId: [],
    empresaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productoService: ProductoService,
    protected adjuntoService: AdjuntoService,
    protected userService: UserService,
    protected proveedorService: ProveedorService,
    protected tipoArticuloService: TipoArticuloService,
    protected categoriaService: CategoriaService,
    protected seccionService: SeccionService,
    protected estatusService: EstatusService,
    protected unidadMedidaService: UnidadMedidaService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ producto }) => {
      this.updateForm(producto);
    });
    this.adjuntoService
      .query({ filter: 'producto-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IAdjunto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAdjunto[]>) => response.body)
      )
      .subscribe(
        (res: IAdjunto[]) => {
          if (!this.editForm.get('adjuntoId').value) {
            this.adjuntos = res;
          } else {
            this.adjuntoService
              .find(this.editForm.get('adjuntoId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IAdjunto>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IAdjunto>) => subResponse.body)
              )
              .subscribe(
                (subRes: IAdjunto) => (this.adjuntos = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.proveedorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProveedor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProveedor[]>) => response.body)
      )
      .subscribe((res: IProveedor[]) => (this.proveedors = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tipoArticuloService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipoArticulo[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipoArticulo[]>) => response.body)
      )
      .subscribe((res: ITipoArticulo[]) => (this.tipoarticulos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.categoriaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICategoria[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICategoria[]>) => response.body)
      )
      .subscribe((res: ICategoria[]) => (this.categorias = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.seccionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISeccion[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISeccion[]>) => response.body)
      )
      .subscribe((res: ISeccion[]) => (this.seccions = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.estatusService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstatus[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstatus[]>) => response.body)
      )
      .subscribe((res: IEstatus[]) => (this.estatuses = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.unidadMedidaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUnidadMedida[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUnidadMedida[]>) => response.body)
      )
      .subscribe((res: IUnidadMedida[]) => (this.unidadmedidas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.empresaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEmpresa[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEmpresa[]>) => response.body)
      )
      .subscribe((res: IEmpresa[]) => (this.empresas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(producto: IProducto) {
    this.editForm.patchValue({
      id: producto.id,
      nombre: producto.nombre,
      descripcion: producto.descripcion,
      caracteristicas: producto.caracteristicas,
      precioSinIva: producto.precioSinIva,
      precio: producto.precio,
      fechaAlta: producto.fechaAlta != null ? producto.fechaAlta.format(DATE_TIME_FORMAT) : null,
      fechaModificacion: producto.fechaModificacion != null ? producto.fechaModificacion.format(DATE_TIME_FORMAT) : null,
      adjuntoId: producto.adjuntoId,
      usuarioAltaId: producto.usuarioAltaId,
      usuarioModificacionId: producto.usuarioModificacionId,
      proveedorId: producto.proveedorId,
      tipoArticuloId: producto.tipoArticuloId,
      categoriaId: producto.categoriaId,
      seccionId: producto.seccionId,
      estatusId: producto.estatusId,
      unidadMedidaId: producto.unidadMedidaId,
      empresaId: producto.empresaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const producto = this.createFromForm();
    if (producto.id !== undefined) {
      this.subscribeToSaveResponse(this.productoService.update(producto));
    } else {
      this.subscribeToSaveResponse(this.productoService.create(producto));
    }
  }

  private createFromForm(): IProducto {
    return {
      ...new Producto(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      caracteristicas: this.editForm.get(['caracteristicas']).value,
      precioSinIva: this.editForm.get(['precioSinIva']).value,
      precio: this.editForm.get(['precio']).value,
      fechaAlta:
        this.editForm.get(['fechaAlta']).value != null ? moment(this.editForm.get(['fechaAlta']).value, DATE_TIME_FORMAT) : undefined,
      fechaModificacion:
        this.editForm.get(['fechaModificacion']).value != null
          ? moment(this.editForm.get(['fechaModificacion']).value, DATE_TIME_FORMAT)
          : undefined,
      adjuntoId: this.editForm.get(['adjuntoId']).value,
      usuarioAltaId: this.editForm.get(['usuarioAltaId']).value,
      usuarioModificacionId: this.editForm.get(['usuarioModificacionId']).value,
      proveedorId: this.editForm.get(['proveedorId']).value,
      tipoArticuloId: this.editForm.get(['tipoArticuloId']).value,
      categoriaId: this.editForm.get(['categoriaId']).value,
      seccionId: this.editForm.get(['seccionId']).value,
      estatusId: this.editForm.get(['estatusId']).value,
      unidadMedidaId: this.editForm.get(['unidadMedidaId']).value,
      empresaId: this.editForm.get(['empresaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducto>>) {
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

  trackAdjuntoById(index: number, item: IAdjunto) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackProveedorById(index: number, item: IProveedor) {
    return item.id;
  }

  trackTipoArticuloById(index: number, item: ITipoArticulo) {
    return item.id;
  }

  trackCategoriaById(index: number, item: ICategoria) {
    return item.id;
  }

  trackSeccionById(index: number, item: ISeccion) {
    return item.id;
  }

  trackEstatusById(index: number, item: IEstatus) {
    return item.id;
  }

  trackUnidadMedidaById(index: number, item: IUnidadMedida) {
    return item.id;
  }

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }
}
