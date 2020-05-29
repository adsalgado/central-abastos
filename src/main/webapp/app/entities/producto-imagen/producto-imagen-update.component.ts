import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProductoImagen, ProductoImagen } from 'app/shared/model/producto-imagen.model';
import { ProductoImagenService } from './producto-imagen.service';
import { IUser, UserService } from 'app/core';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { IAdjunto, Adjunto } from 'app/shared/model/adjunto.model';
import { AdjuntoService } from 'app/entities/adjunto';

@Component({
  selector: 'jhi-producto-imagen-update',
  templateUrl: './producto-imagen-update.component.html'
})
export class ProductoImagenUpdateComponent implements OnInit {
  isSaving: boolean;
  users: IUser[];
  actualizarAdjunto: boolean;
  productoProveedorId: number;
  productoImagen: IProductoImagen;

  editForm = this.fb.group({
    id: [],
    producto: [],
    productoProveedorId: [],
    adjuntoId: [],
    file: [],
    fileName: [],
    fileContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected productoImagenService: ProductoImagenService,
    protected userService: UserService,
    protected productoService: ProductoService,
    protected adjuntoService: AdjuntoService,
    protected activatedRoute: ActivatedRoute,
    protected elementRef: ElementRef,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    let sId = this.activatedRoute.snapshot.paramMap.get('productoProveedorId');
    if (!isNaN(Number(sId))) {
      this.productoProveedorId = Number(sId);
    } else {
      console.log('Not a Number');
    }
    this.actualizarAdjunto = false;
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productoImagen }) => {
      this.productoImagen = productoImagen;
      this.updateForm(productoImagen);
    });
  }

  updateForm(productoImagen: IProductoImagen) {
    this.editForm.patchValue({
      id: productoImagen.id,
      producto: this.productoProveedorId,
      productoProveedorId: this.productoProveedorId,
      adjuntoId: productoImagen.adjuntoId
    });
    if (productoImagen.adjunto) {
      this.editForm.patchValue({
        file: productoImagen.adjunto.file,
        fileContentType: productoImagen.adjunto.fileContentType,
        fileName: productoImagen.adjunto.fileName
      });
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productoImagen = this.createFromForm();
    if (productoImagen.id !== undefined) {
      this.subscribeToSaveResponse(this.productoImagenService.update(productoImagen));
    } else {
      this.subscribeToSaveResponse(this.productoImagenService.create(productoImagen));
    }
  }

  private createFromForm(): IProductoImagen {
    let productoImagen = {
      ...new ProductoImagen(),
      id: this.editForm.get(['id']).value,
      productoProveedorId: this.editForm.get(['productoProveedorId']).value,
      adjuntoId: this.editForm.get(['adjuntoId']).value
    };

    if (this.actualizarAdjunto && this.editForm.get(['file'])) {
      let adjunto = new Adjunto();
      adjunto.id = this.editForm.get(['adjuntoId']).value;
      adjunto.file = this.editForm.get(['file']).value;
      adjunto.contentType = this.editForm.get(['fileContentType']).value;
      adjunto.fileContentType = this.editForm.get(['fileContentType']).value;
      adjunto.fileName = this.editForm.get(['fileName']).value;
      productoImagen.adjunto = adjunto;
    }

    return productoImagen;
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductoImagen>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }

  trackAdjuntoById(index: number, item: IAdjunto) {
    return item.id;
  }
}
