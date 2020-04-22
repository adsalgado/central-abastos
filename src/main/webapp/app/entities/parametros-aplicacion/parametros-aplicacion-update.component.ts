import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IParametrosAplicacion, ParametrosAplicacion } from 'app/shared/model/parametros-aplicacion.model';
import { ParametrosAplicacionService } from './parametros-aplicacion.service';
import { IAdjunto } from 'app/shared/model/adjunto.model';
import { AdjuntoService } from 'app/entities/adjunto';

@Component({
  selector: 'jhi-parametros-aplicacion-update',
  templateUrl: './parametros-aplicacion-update.component.html'
})
export class ParametrosAplicacionUpdateComponent implements OnInit {
  isSaving: boolean;

  adjuntos: IAdjunto[];

  editForm = this.fb.group({
    id: [],
    clave: [null, [Validators.required, Validators.maxLength(128)]],
    descripcion: [null, [Validators.maxLength(256)]],
    adjuntoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected parametrosAplicacionService: ParametrosAplicacionService,
    protected adjuntoService: AdjuntoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ parametrosAplicacion }) => {
      this.updateForm(parametrosAplicacion);
    });
    this.adjuntoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAdjunto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAdjunto[]>) => response.body)
      )
      .subscribe((res: IAdjunto[]) => (this.adjuntos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(parametrosAplicacion: IParametrosAplicacion) {
    this.editForm.patchValue({
      id: parametrosAplicacion.id,
      clave: parametrosAplicacion.clave,
      descripcion: parametrosAplicacion.descripcion,
      adjuntoId: parametrosAplicacion.adjuntoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const parametrosAplicacion = this.createFromForm();
    if (parametrosAplicacion.id !== undefined) {
      this.subscribeToSaveResponse(this.parametrosAplicacionService.update(parametrosAplicacion));
    } else {
      this.subscribeToSaveResponse(this.parametrosAplicacionService.create(parametrosAplicacion));
    }
  }

  private createFromForm(): IParametrosAplicacion {
    return {
      ...new ParametrosAplicacion(),
      id: this.editForm.get(['id']).value,
      clave: this.editForm.get(['clave']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      adjuntoId: this.editForm.get(['adjuntoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParametrosAplicacion>>) {
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
}
