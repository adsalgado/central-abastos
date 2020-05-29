import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPromocion, Promocion } from 'app/shared/model/promocion.model';
import { PromocionService } from './promocion.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';
import { environment } from '../../../environments/environment.prod';
import { AdjuntoService } from '../adjunto/adjunto.service';
import { IAdjunto, Adjunto } from 'app/shared/model/adjunto.model';

@Component({
  selector: 'jhi-promocion-update',
  templateUrl: './promocion-update.component.html'
})
export class PromocionUpdateComponent implements OnInit {
  isSaving: boolean;
  empresas: IEmpresa[];
  env: any = environment;
  actualizarAdjunto: boolean;

  editForm = this.fb.group({
    id: [],
    titulo: [null, [Validators.required, Validators.maxLength(128)]],
    descripcion: [null, [Validators.maxLength(128)]],
    link: [],
    empresaId: [],
    adjuntoId: [],
    file: [],
    fileName: [],
    fileContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected promocionService: PromocionService,
    protected empresaService: EmpresaService,
    protected adjuntoService: AdjuntoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.actualizarAdjunto = false;
    this.activatedRoute.data.subscribe(({ promocion }) => {
      if (promocion.adjuntoId) {
        this.adjuntoService.find(promocion.adjuntoId).subscribe(
          (res: HttpResponse<IAdjunto>) => {
            promocion.adjunto = res.body;
            this.updateForm(promocion);
          },
          (error: HttpErrorResponse) => console.log(error)
        );
      } else {
        this.updateForm(promocion);
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

  updateForm(promocion: IPromocion) {
    this.editForm.patchValue({
      id: promocion.id,
      titulo: promocion.titulo,
      descripcion: promocion.descripcion,
      link: promocion.link,
      adjuntoId: promocion.adjuntoId
    });
    if (promocion.adjunto) {
      this.editForm.patchValue({
        file: promocion.adjunto.file,
        fileContentType: promocion.adjunto.fileContentType,
        fileName: promocion.adjunto.fileName
      });
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const promocion = this.createFromForm();
    if (promocion.id !== undefined) {
      this.subscribeToSaveResponse(this.promocionService.update(promocion));
    } else {
      this.subscribeToSaveResponse(this.promocionService.create(promocion));
    }
  }

  private createFromForm(): IPromocion {
    let promocion = {
      ...new Promocion(),
      id: this.editForm.get(['id']).value,
      titulo: this.editForm.get(['titulo']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      link: this.editForm.get(['link']).value,
      adjuntoId: this.editForm.get(['adjuntoId']).value
    };

    if (this.actualizarAdjunto && this.editForm.get(['file'])) {
      let adjunto = new Adjunto();
      adjunto.id = this.editForm.get(['adjuntoId']).value;
      adjunto.file = this.editForm.get(['file']).value;
      adjunto.contentType = this.editForm.get(['fileContentType']).value;
      adjunto.fileContentType = this.editForm.get(['fileContentType']).value;
      adjunto.fileName = this.editForm.get(['fileName']).value;
      promocion.adjunto = adjunto;
    }

    return promocion;
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPromocion>>) {
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
