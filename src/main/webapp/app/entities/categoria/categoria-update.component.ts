import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ICategoria, Categoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from './categoria.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';
import { SeccionService } from '../seccion';
import { ISeccion } from 'app/shared/model/seccion.model';
import { IAdjunto, Adjunto } from 'app/shared/model/adjunto.model';
import { AdjuntoService } from '../adjunto/adjunto.service';

@Component({
  selector: 'jhi-categoria-update',
  templateUrl: './categoria-update.component.html'
})
export class CategoriaUpdateComponent implements OnInit {
  isSaving: boolean;
  secciones: ISeccion[];
  actualizarAdjunto: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.maxLength(128)]],
    descripcion: [null, [Validators.maxLength(128)]],
    icono: [],
    seccionId: [],
    adjuntoId: [],
    file: [],
    fileName: [],
    fileContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected categoriaService: CategoriaService,
    protected seccionService: SeccionService,
    protected adjuntoService: AdjuntoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.actualizarAdjunto = false;
    this.activatedRoute.data.subscribe(({ categoria }) => {
      if (categoria.adjuntoId) {
        this.adjuntoService.find(categoria.adjuntoId).subscribe(
          (res: HttpResponse<IAdjunto>) => {
            categoria.adjunto = res.body;
            this.updateForm(categoria);
          },
          (error: HttpErrorResponse) => console.log(error)
        );
      } else {
        this.updateForm(categoria);
      }
    });
    this.seccionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISeccion[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISeccion[]>) => response.body)
      )
      .subscribe((res: ISeccion[]) => (this.secciones = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(categoria: ICategoria) {
    this.editForm.patchValue({
      id: categoria.id,
      icono: categoria.icono,
      nombre: categoria.nombre,
      descripcion: categoria.descripcion,
      seccionId: categoria.seccionId,
      adjuntoId: categoria.adjuntoId
    });
    if (categoria.adjunto) {
      this.editForm.patchValue({
        file: categoria.adjunto.file,
        fileContentType: categoria.adjunto.fileContentType,
        fileName: categoria.adjunto.fileName
      });
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const categoria = this.createFromForm();
    if (categoria.id !== undefined) {
      this.subscribeToSaveResponse(this.categoriaService.update(categoria));
    } else {
      this.subscribeToSaveResponse(this.categoriaService.create(categoria));
    }
  }

  private createFromForm(): ICategoria {
    let categoria = {
      ...new Categoria(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      icono: this.editForm.get(['icono']).value,
      seccionId: this.editForm.get(['seccionId']).value,
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
      categoria.adjunto = adjunto;
    }

    return categoria;
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoria>>) {
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

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }
}
