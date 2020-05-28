import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITipoArticulo, TipoArticulo } from 'app/shared/model/tipo-articulo.model';
import { TipoArticuloService } from './tipo-articulo.service';
import { ICategoria } from 'app/shared/model/categoria.model';
import { JhiDataUtils, JhiAlertService } from 'ng-jhipster';
import { CategoriaService } from '../categoria';
import { IAdjunto, Adjunto } from 'app/shared/model/adjunto.model';
import { AdjuntoService } from '../adjunto/adjunto.service';
import { filter, map } from 'rxjs/operators';

@Component({
  selector: 'jhi-tipo-articulo-update',
  templateUrl: './tipo-articulo-update.component.html'
})
export class TipoArticuloUpdateComponent implements OnInit {
  isSaving: boolean;
  categorias: ICategoria[];
  actualizarAdjunto: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.maxLength(128)]],
    descripcion: [null, [Validators.maxLength(128)]],
    categoriaId: [],
    adjuntoId: [],
    file: [],
    fileName: [],
    fileContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected tipoArticuloService: TipoArticuloService,
    protected categoriaService: CategoriaService,
    protected adjuntoService: AdjuntoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.actualizarAdjunto = false;

    this.activatedRoute.data.subscribe(({ tipoArticulo }) => {
      if (tipoArticulo.adjuntoId) {
        this.adjuntoService.find(tipoArticulo.adjuntoId).subscribe(
          (res: HttpResponse<IAdjunto>) => {
            tipoArticulo.adjunto = res.body;
            this.updateForm(tipoArticulo);
          },
          (error: HttpErrorResponse) => console.log(error)
        );
      } else {
        this.updateForm(tipoArticulo);
      }
    });
    this.categoriaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICategoria[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICategoria[]>) => response.body)
      )
      .subscribe((res: ICategoria[]) => (this.categorias = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(tipoArticulo: ITipoArticulo) {
    this.editForm.patchValue({
      id: tipoArticulo.id,
      nombre: tipoArticulo.nombre,
      descripcion: tipoArticulo.descripcion,
      categoriaId: tipoArticulo.categoriaId,
      adjuntoId: tipoArticulo.adjuntoId
    });
    if (tipoArticulo.adjunto) {
      this.editForm.patchValue({
        file: tipoArticulo.adjunto.file,
        fileContentType: tipoArticulo.adjunto.fileContentType,
        fileName: tipoArticulo.adjunto.fileName
      });
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tipoArticulo = this.createFromForm();
    if (tipoArticulo.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoArticuloService.update(tipoArticulo));
    } else {
      this.subscribeToSaveResponse(this.tipoArticuloService.create(tipoArticulo));
    }
  }

  private createFromForm(): ITipoArticulo {
    let tipoArticulo = {
      ...new TipoArticulo(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      categoriaId: this.editForm.get(['categoriaId']).value,
      adjuntoId: this.editForm.get(['adjuntoId']).value
    };

    if (this.actualizarAdjunto && this.editForm.get(['file'])) {
      let adjunto = {
        ...new Adjunto(),
        id: this.editForm.get(['adjuntoId']).value,
        file: this.editForm.get(['file']).value,
        contentType: this.editForm.get(['fileContentType']).value,
        fileContentType: this.editForm.get(['fileContentType']).value,
        fileName: this.editForm.get(['fileName']).value
      };
      tipoArticulo.adjunto = adjunto;
    }

    return tipoArticulo;
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
          //console.log(file);
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoArticulo>>) {
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
}
