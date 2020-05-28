import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ISeccion, Seccion } from 'app/shared/model/seccion.model';
import { SeccionService } from './seccion.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';
import { environment } from '../../../environments/environment.prod';
import { AdjuntoService } from '../adjunto/adjunto.service';
import { IAdjunto, Adjunto } from 'app/shared/model/adjunto.model';

@Component({
  selector: 'jhi-seccion-update',
  templateUrl: './seccion-update.component.html'
})
export class SeccionUpdateComponent implements OnInit {
  isSaving: boolean;
  empresas: IEmpresa[];
  env: any = environment;
  actualizarAdjunto: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.maxLength(128)]],
    descripcion: [null, [Validators.maxLength(128)]],
    icono: [],
    empresaId: [],
    adjuntoId: [],
    file: [],
    fileName: [],
    fileContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected seccionService: SeccionService,
    protected empresaService: EmpresaService,
    protected adjuntoService: AdjuntoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.actualizarAdjunto = false;
    this.activatedRoute.data.subscribe(({ seccion }) => {
      if (seccion.adjuntoId) {
        this.adjuntoService.find(seccion.adjuntoId).subscribe(
          (res: HttpResponse<IAdjunto>) => {
            seccion.adjunto = res.body;
            this.updateForm(seccion);
          },
          (error: HttpErrorResponse) => console.log(error)
        );
      } else {
        this.updateForm(seccion);
      }
    });
    this.empresaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEmpresa[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEmpresa[]>) => response.body)
      )
      .subscribe((res: IEmpresa[]) => (this.empresas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(seccion: ISeccion) {
    this.editForm.patchValue({
      id: seccion.id,
      nombre: seccion.nombre,
      icono: seccion.icono,
      descripcion: seccion.descripcion,
      empresaId: seccion.empresaId,
      adjuntoId: seccion.adjuntoId
    });
    if (seccion.adjunto) {
      this.editForm.patchValue({
        file: seccion.adjunto.file,
        fileContentType: seccion.adjunto.fileContentType,
        fileName: seccion.adjunto.fileName
      });
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const seccion = this.createFromForm();
    if (seccion.id !== undefined) {
      this.subscribeToSaveResponse(this.seccionService.update(seccion));
    } else {
      this.subscribeToSaveResponse(this.seccionService.create(seccion));
    }
  }

  private createFromForm(): ISeccion {
    let seccion = {
      ...new Seccion(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      icono: this.editForm.get(['icono']).value,
      empresaId: this.editForm.get(['empresaId']).value,
      adjuntoId: this.editForm.get(['adjuntoId']).value
    };

    if (this.actualizarAdjunto && this.editForm.get(['file'])) {
      let adjunto = new Adjunto();
      adjunto.id = this.editForm.get(['adjuntoId']).value;
      adjunto.file = this.editForm.get(['file']).value;
      adjunto.contentType = this.editForm.get(['fileContentType']).value;
      adjunto.fileContentType = this.editForm.get(['fileContentType']).value;
      adjunto.fileName = this.editForm.get(['fileName']).value;
      seccion.adjunto = adjunto;
    }

    return seccion;
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISeccion>>) {
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
