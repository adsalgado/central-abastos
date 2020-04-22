import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPagos, Pagos } from 'app/shared/model/pagos.model';
import { PagosService } from './pagos.service';

@Component({
  selector: 'jhi-pagos-update',
  templateUrl: './pagos-update.component.html'
})
export class PagosUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: []
  });

  constructor(protected pagosService: PagosService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pagos }) => {
      this.updateForm(pagos);
    });
  }

  updateForm(pagos: IPagos) {
    this.editForm.patchValue({
      id: pagos.id
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pagos = this.createFromForm();
    if (pagos.id !== undefined) {
      this.subscribeToSaveResponse(this.pagosService.update(pagos));
    } else {
      this.subscribeToSaveResponse(this.pagosService.create(pagos));
    }
  }

  private createFromForm(): IPagos {
    return {
      ...new Pagos(),
      id: this.editForm.get(['id']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPagos>>) {
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
