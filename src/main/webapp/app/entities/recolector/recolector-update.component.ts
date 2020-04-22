import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IRecolector, Recolector } from 'app/shared/model/recolector.model';
import { RecolectorService } from './recolector.service';
import { IUser, UserService } from 'app/core';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';

@Component({
  selector: 'jhi-recolector-update',
  templateUrl: './recolector-update.component.html'
})
export class RecolectorUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  empresas: IEmpresa[];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.maxLength(128)]],
    fechaAlta: [],
    fechaModificacion: [],
    usuarioAltaId: [],
    usuarioModificacionId: [],
    empresaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected recolectorService: RecolectorService,
    protected userService: UserService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ recolector }) => {
      this.updateForm(recolector);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.empresaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEmpresa[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEmpresa[]>) => response.body)
      )
      .subscribe((res: IEmpresa[]) => (this.empresas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(recolector: IRecolector) {
    this.editForm.patchValue({
      id: recolector.id,
      nombre: recolector.nombre,
      fechaAlta: recolector.fechaAlta != null ? recolector.fechaAlta.format(DATE_TIME_FORMAT) : null,
      fechaModificacion: recolector.fechaModificacion != null ? recolector.fechaModificacion.format(DATE_TIME_FORMAT) : null,
      usuarioAltaId: recolector.usuarioAltaId,
      usuarioModificacionId: recolector.usuarioModificacionId,
      empresaId: recolector.empresaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const recolector = this.createFromForm();
    if (recolector.id !== undefined) {
      this.subscribeToSaveResponse(this.recolectorService.update(recolector));
    } else {
      this.subscribeToSaveResponse(this.recolectorService.create(recolector));
    }
  }

  private createFromForm(): IRecolector {
    return {
      ...new Recolector(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      fechaAlta:
        this.editForm.get(['fechaAlta']).value != null ? moment(this.editForm.get(['fechaAlta']).value, DATE_TIME_FORMAT) : undefined,
      fechaModificacion:
        this.editForm.get(['fechaModificacion']).value != null
          ? moment(this.editForm.get(['fechaModificacion']).value, DATE_TIME_FORMAT)
          : undefined,
      usuarioAltaId: this.editForm.get(['usuarioAltaId']).value,
      usuarioModificacionId: this.editForm.get(['usuarioModificacionId']).value,
      empresaId: this.editForm.get(['empresaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecolector>>) {
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

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }
}
