import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAdjunto, Adjunto } from 'app/shared/model/adjunto.model';
import { AdjuntoService } from './adjunto.service';

@Component({
  selector: 'jhi-adjunto-update',
  templateUrl: './adjunto-update.component.html'
})
export class AdjuntoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    contentType: [null, [Validators.maxLength(128)]],
    size: [],
    fileName: [null, [Validators.maxLength(128)]],
    file: [],
    fileContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected adjuntoService: AdjuntoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ adjunto }) => {
      this.updateForm(adjunto);
    });
  }

  updateForm(adjunto: IAdjunto) {
    this.editForm.patchValue({
      id: adjunto.id,
      contentType: adjunto.contentType,
      size: adjunto.size,
      fileName: adjunto.fileName,
      file: adjunto.file,
      fileContentType: adjunto.fileContentType
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
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

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const adjunto = this.createFromForm();
    if (adjunto.id !== undefined) {
      this.subscribeToSaveResponse(this.adjuntoService.update(adjunto));
    } else {
      this.subscribeToSaveResponse(this.adjuntoService.create(adjunto));
    }
  }

  private createFromForm(): IAdjunto {
    return {
      ...new Adjunto(),
      id: this.editForm.get(['id']).value,
      contentType: this.editForm.get(['contentType']).value,
      size: this.editForm.get(['size']).value,
      fileName: this.editForm.get(['fileName']).value,
      fileContentType: this.editForm.get(['fileContentType']).value,
      file: this.editForm.get(['file']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdjunto>>) {
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
