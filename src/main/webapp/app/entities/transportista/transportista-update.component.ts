import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ITransportista, Transportista } from 'app/shared/model/transportista.model';
import { TransportistaService } from './transportista.service';
import { IUser, UserService } from 'app/core';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';

@Component({
  selector: 'jhi-transportista-update',
  templateUrl: './transportista-update.component.html'
})
export class TransportistaUpdateComponent implements OnInit {
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
    protected transportistaService: TransportistaService,
    protected userService: UserService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transportista }) => {
      this.updateForm(transportista);
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

  updateForm(transportista: ITransportista) {
    this.editForm.patchValue({
      id: transportista.id,
      nombre: transportista.nombre,
      fechaAlta: transportista.fechaAlta != null ? transportista.fechaAlta.format(DATE_TIME_FORMAT) : null,
      fechaModificacion: transportista.fechaModificacion != null ? transportista.fechaModificacion.format(DATE_TIME_FORMAT) : null,
      usuarioAltaId: transportista.usuarioAltaId,
      usuarioModificacionId: transportista.usuarioModificacionId,
      empresaId: transportista.empresaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transportista = this.createFromForm();
    if (transportista.id !== undefined) {
      this.subscribeToSaveResponse(this.transportistaService.update(transportista));
    } else {
      this.subscribeToSaveResponse(this.transportistaService.create(transportista));
    }
  }

  private createFromForm(): ITransportista {
    return {
      ...new Transportista(),
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransportista>>) {
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
