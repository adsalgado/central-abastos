import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ProductoProveedorService } from './producto-proveedor.service';
import { ProveedorService } from 'app/entities/proveedor';
import { ProductoService } from 'app/entities/producto';
import { EstatusService } from 'app/entities/estatus';
import { TipoArticuloService } from '../tipo-articulo';
import { UnidadMedidaService } from '../unidad-medida';
import { IAdjunto, Adjunto } from 'app/shared/model/adjunto.model';
import { IAbastosResponse } from 'app/shared/model/abastos-response.model';
import { IErrorValidacion } from 'app/shared/model/error-validacion';

@Component({
  selector: 'jhi-producto-proveedor-carga-masiva',
  templateUrl: './producto-proveedor-carga-masiva.component.html'
})
export class ProductoProveedorCargaMasivaComponent implements OnInit {
  isSaving: boolean;
  fileName: string;
  validationErrors: IErrorValidacion[];
  editForm = this.fb.group({
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
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    /*this.activatedRoute.data.subscribe(({ productoProveedor }) => {
      this.updateForm(productoProveedor);
    });*/
  }

  updateForm(adjunto: IAdjunto) {
    this.editForm.patchValue({
      /*file: adjunto.file*/
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    //const productoProveedor = this.createFromForm();
    const adjunto = this.createFromForm();
    //console.log(adjunto);
    this.subscribeToSaveResponse(this.productoProveedorService.cargaMasiva(adjunto));
  }

  private createFromForm(): IAdjunto {
    return {
      ...new Adjunto(),
      file: this.editForm.get(['file']).value,
      fileName: this.fileName
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAbastosResponse>>) {
    result.subscribe(result => this.onSaveSuccess(result), result => this.onSaveError(result));
  }

  protected onSaveSuccess(result: HttpResponse<IAbastosResponse>) {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(result) {
    this.isSaving = false;
    if (result.status === 400) {
      this.validationErrors = result.error.data;
      console.log(this.validationErrors);
    }
    this.jhiAlertService.error('Existen errores en el archivo de carga.', null, null);
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        this.fileName = file.name;
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
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
}
