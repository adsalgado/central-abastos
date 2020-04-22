import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITipoArticulo, TipoArticulo } from 'app/shared/model/tipo-articulo.model';
import { TipoArticuloService } from './tipo-articulo.service';

@Component({
  selector: 'jhi-tipo-articulo-update',
  templateUrl: './tipo-articulo-update.component.html'
})
export class TipoArticuloUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.maxLength(128)]]
  });

  constructor(protected tipoArticuloService: TipoArticuloService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tipoArticulo }) => {
      this.updateForm(tipoArticulo);
    });
  }

  updateForm(tipoArticulo: ITipoArticulo) {
    this.editForm.patchValue({
      id: tipoArticulo.id,
      nombre: tipoArticulo.nombre
    });
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
    return {
      ...new TipoArticulo(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value
    };
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
}
