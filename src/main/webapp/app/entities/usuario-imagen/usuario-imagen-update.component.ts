import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IUsuarioImagen, UsuarioImagen } from 'app/shared/model/usuario-imagen.model';
import { UsuarioImagenService } from './usuario-imagen.service';
import { IUser, UserService } from 'app/core';
import { IAdjunto } from 'app/shared/model/adjunto.model';
import { AdjuntoService } from 'app/entities/adjunto';

@Component({
  selector: 'jhi-usuario-imagen-update',
  templateUrl: './usuario-imagen-update.component.html'
})
export class UsuarioImagenUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  adjuntos: IAdjunto[];

  editForm = this.fb.group({
    id: [],
    fechaAlta: [],
    usuarioId: [],
    adjuntoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected usuarioImagenService: UsuarioImagenService,
    protected userService: UserService,
    protected adjuntoService: AdjuntoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ usuarioImagen }) => {
      this.updateForm(usuarioImagen);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.adjuntoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAdjunto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAdjunto[]>) => response.body)
      )
      .subscribe((res: IAdjunto[]) => (this.adjuntos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(usuarioImagen: IUsuarioImagen) {
    this.editForm.patchValue({
      id: usuarioImagen.id,
      fechaAlta: usuarioImagen.fechaAlta != null ? usuarioImagen.fechaAlta.format(DATE_TIME_FORMAT) : null,
      usuarioId: usuarioImagen.usuarioId,
      adjuntoId: usuarioImagen.adjuntoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const usuarioImagen = this.createFromForm();
    if (usuarioImagen.id !== undefined) {
      this.subscribeToSaveResponse(this.usuarioImagenService.update(usuarioImagen));
    } else {
      this.subscribeToSaveResponse(this.usuarioImagenService.create(usuarioImagen));
    }
  }

  private createFromForm(): IUsuarioImagen {
    return {
      ...new UsuarioImagen(),
      id: this.editForm.get(['id']).value,
      fechaAlta:
        this.editForm.get(['fechaAlta']).value != null ? moment(this.editForm.get(['fechaAlta']).value, DATE_TIME_FORMAT) : undefined,
      usuarioId: this.editForm.get(['usuarioId']).value,
      adjuntoId: this.editForm.get(['adjuntoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsuarioImagen>>) {
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

  trackAdjuntoById(index: number, item: IAdjunto) {
    return item.id;
  }
}
