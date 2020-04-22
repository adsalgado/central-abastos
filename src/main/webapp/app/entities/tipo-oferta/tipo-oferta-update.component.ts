import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITipoOferta, TipoOferta } from 'app/shared/model/tipo-oferta.model';
import { TipoOfertaService } from './tipo-oferta.service';

@Component({
  selector: 'jhi-tipo-oferta-update',
  templateUrl: './tipo-oferta-update.component.html'
})
export class TipoOfertaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.maxLength(128)]],
    descripcion: [null, [Validators.maxLength(256)]]
  });

  constructor(protected tipoOfertaService: TipoOfertaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tipoOferta }) => {
      this.updateForm(tipoOferta);
    });
  }

  updateForm(tipoOferta: ITipoOferta) {
    this.editForm.patchValue({
      id: tipoOferta.id,
      nombre: tipoOferta.nombre,
      descripcion: tipoOferta.descripcion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tipoOferta = this.createFromForm();
    if (tipoOferta.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoOfertaService.update(tipoOferta));
    } else {
      this.subscribeToSaveResponse(this.tipoOfertaService.create(tipoOferta));
    }
  }

  private createFromForm(): ITipoOferta {
    return {
      ...new TipoOferta(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoOferta>>) {
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
