import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProductoProveedor, ProductoProveedor } from 'app/shared/model/producto-proveedor.model';
import { ProductoProveedorService } from './producto-proveedor.service';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor';
import { IProducto, Producto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { IEstatus } from 'app/shared/model/estatus.model';
import { EstatusService } from 'app/entities/estatus';
import { ITipoArticulo } from 'app/shared/model/tipo-articulo.model';
import { IUnidadMedida } from 'app/shared/model/unidad-medida.model';
import { TipoArticuloService } from '../tipo-articulo';
import { UnidadMedidaService } from '../unidad-medida';
import { IAdjunto, Adjunto } from 'app/shared/model/adjunto.model';
import { AdjuntoService } from '../adjunto/adjunto.service';

@Component({
  selector: 'jhi-producto-proveedor-update',
  templateUrl: './producto-proveedor-update.component.html'
})
export class ProductoProveedorUpdateComponent implements OnInit {
  isSaving: boolean;
  proveedors: IProveedor[];
  productos: IProducto[];
  estatuses: IEstatus[];
  tipoarticulos: ITipoArticulo[];
  unidadmedidas: IUnidadMedida[];
  actualizarAdjunto: boolean;
  tipoEstatus: string = 'ESTATUS_PRODUCTO';

  editForm = this.fb.group({
    id: [],
    sku: [null, [Validators.maxLength(45)]],
    nombre: [null, [Validators.required, Validators.maxLength(256)]],
    descripcion: [null, [Validators.required, Validators.maxLength(512)]],
    caracteristicas: [null, [Validators.maxLength(512)]],
    precio: [null, [Validators.required]],
    tipoArticuloId: [],
    estatusId: [],
    unidadMedidaId: [],
    adjuntoId: [],
    file: [],
    fileName: [],
    fileContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected productoProveedorService: ProductoProveedorService,
    protected proveedorService: ProveedorService,
    protected productoService: ProductoService,
    protected estatusService: EstatusService,
    protected tipoArticuloService: TipoArticuloService,
    protected unidadMedidaService: UnidadMedidaService,
    protected adjuntoService: AdjuntoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.actualizarAdjunto = false;
    this.activatedRoute.data.subscribe(({ productoProveedor }) => {
      console.log(productoProveedor);
      if (productoProveedor.producto.adjuntoId) {
        this.adjuntoService.find(productoProveedor.producto.adjuntoId).subscribe(
          (res: HttpResponse<IAdjunto>) => {
            productoProveedor.producto.adjunto = res.body;
            this.updateForm(productoProveedor);
          },
          (error: HttpErrorResponse) => console.log(error)
        );
      } else {
        this.updateForm(productoProveedor);
      }
    });
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
    this.unidadMedidaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUnidadMedida[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUnidadMedida[]>) => response.body)
      )
      .subscribe((res: IUnidadMedida[]) => (this.unidadmedidas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.estatusService
      .findByTipoEstatus(this.tipoEstatus)
      .pipe(
        filter((mayBeOk: HttpResponse<IEstatus[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstatus[]>) => response.body)
      )
      .subscribe((res: IEstatus[]) => (this.estatuses = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(productoProveedor: IProductoProveedor) {
    if (!productoProveedor.producto) {
      productoProveedor.producto = new Producto();
    }
    this.editForm.patchValue({
      id: productoProveedor.id,
      sku: productoProveedor.producto.sku,
      nombre: productoProveedor.producto.nombre,
      descripcion: productoProveedor.producto.descripcion,
      caracteristicas: productoProveedor.producto.caracteristicas,
      precio: productoProveedor.precio,
      adjuntoId: productoProveedor.producto.adjuntoId,
      tipoArticuloId: productoProveedor.producto.tipoArticuloId,
      estatusId: productoProveedor.estatusId,
      unidadMedidaId: productoProveedor.producto.unidadMedidaId
    });
    if (productoProveedor.producto.adjunto) {
      this.editForm.patchValue({
        file: productoProveedor.producto.adjunto.file,
        fileContentType: productoProveedor.producto.adjunto.fileContentType,
        fileName: productoProveedor.producto.adjunto.fileName
      });
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productoProveedor = this.createFromForm();
    if (productoProveedor.id !== undefined && productoProveedor.id !== null) {
      this.subscribeToSaveResponse(this.productoProveedorService.update(productoProveedor));
    } else {
      this.subscribeToSaveResponse(this.productoProveedorService.create(productoProveedor));
    }
  }

  private createFromForm(): IProductoProveedor {
    let producto = {
      ...new Producto(),
      sku: this.editForm.get(['sku']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      caracteristicas: this.editForm.get(['caracteristicas']).value,
      tipoArticuloId: this.editForm.get(['tipoArticuloId']).value,
      unidadMedidaId: this.editForm.get(['unidadMedidaId']).value
    };

    if (this.actualizarAdjunto && this.editForm.get(['file'])) {
      let adjunto = new Adjunto();
      adjunto.id = this.editForm.get(['adjuntoId']).value;
      adjunto.file = this.editForm.get(['file']).value;
      adjunto.contentType = this.editForm.get(['fileContentType']).value;
      adjunto.fileContentType = this.editForm.get(['fileContentType']).value;
      adjunto.fileName = this.editForm.get(['fileName']).value;
      producto.adjunto = adjunto;
    }

    let productoProveedor = {
      ...new ProductoProveedor(),
      id: this.editForm.get(['id']).value,
      precio: this.editForm.get(['precio']).value,
      precioSinIva: this.editForm.get(['precio']).value,
      estatusId: this.editForm.get(['estatusId']).value,
      producto: producto
    };
    console.log(productoProveedor);
    return productoProveedor;
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          const filedName: string = field + 'Name';
          this.dataUtils.toBase64(file, base64Data => {
            this.actualizarAdjunto = true;
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type,
              [filedName]: file.name
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
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
