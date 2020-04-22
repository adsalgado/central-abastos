import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IEstatus, Estatus } from 'app/shared/model/estatus.model';
import { EstatusService } from './estatus.service';

@Component({
  selector: 'jhi-estatus-update',
  templateUrl: './estatus-update.component.html'
})
export class EstatusUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    tipoEstatus: [],
    nombre: [null, [Validators.required, Validators.maxLength(128)]]
  });

  constructor(protected estatusService: EstatusService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ estatus }) => {
      this.updateForm(estatus);
    });
  }

  updateForm(estatus: IEstatus) {
    this.editForm.patchValue({
      id: estatus.id,
      tipoEstatus: estatus.tipoEstatus,
      nombre: estatus.nombre
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const estatus = this.createFromForm();
    if (estatus.id !== undefined) {
      this.subscribeToSaveResponse(this.estatusService.update(estatus));
    } else {
      this.subscribeToSaveResponse(this.estatusService.create(estatus));
    }
  }

  private createFromForm(): IEstatus {
    return {
      ...new Estatus(),
      id: this.editForm.get(['id']).value,
      tipoEstatus: this.editForm.get(['tipoEstatus']).value,
      nombre: this.editForm.get(['nombre']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstatus>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
