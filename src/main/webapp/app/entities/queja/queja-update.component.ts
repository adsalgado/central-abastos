import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IQueja, Queja } from 'app/shared/model/queja.model';
import { QuejaService } from './queja.service';

@Component({
  selector: 'jhi-queja-update',
  templateUrl: './queja-update.component.html'
})
export class QuejaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: []
  });

  constructor(protected quejaService: QuejaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ queja }) => {
      this.updateForm(queja);
    });
  }

  updateForm(queja: IQueja) {
    this.editForm.patchValue({
      id: queja.id
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const queja = this.createFromForm();
    if (queja.id !== undefined) {
      this.subscribeToSaveResponse(this.quejaService.update(queja));
    } else {
      this.subscribeToSaveResponse(this.quejaService.create(queja));
    }
  }

  private createFromForm(): IQueja {
    return {
      ...new Queja(),
      id: this.editForm.get(['id']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQueja>>) {
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
