import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRecolectorTarifa, RecolectorTarifa } from 'app/shared/model/recolector-tarifa.model';
import { RecolectorTarifaService } from './recolector-tarifa.service';
import { IRecolector } from 'app/shared/model/recolector.model';
import { RecolectorService } from 'app/entities/recolector';

@Component({
  selector: 'jhi-recolector-tarifa-update',
  templateUrl: './recolector-tarifa-update.component.html'
})
export class RecolectorTarifaUpdateComponent implements OnInit {
  isSaving: boolean;

  recolectors: IRecolector[];

  editForm = this.fb.group({
    id: [],
    rangoMinimo: [null, [Validators.required]],
    rangoMaximo: [null, [Validators.required]],
    precio: [null, [Validators.required]],
    recolectorId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected recolectorTarifaService: RecolectorTarifaService,
    protected recolectorService: RecolectorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ recolectorTarifa }) => {
      this.updateForm(recolectorTarifa);
    });
    this.recolectorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRecolector[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRecolector[]>) => response.body)
      )
      .subscribe((res: IRecolector[]) => (this.recolectors = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(recolectorTarifa: IRecolectorTarifa) {
    this.editForm.patchValue({
      id: recolectorTarifa.id,
      rangoMinimo: recolectorTarifa.rangoMinimo,
      rangoMaximo: recolectorTarifa.rangoMaximo,
      precio: recolectorTarifa.precio,
      recolectorId: recolectorTarifa.recolectorId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const recolectorTarifa = this.createFromForm();
    if (recolectorTarifa.id !== undefined) {
      this.subscribeToSaveResponse(this.recolectorTarifaService.update(recolectorTarifa));
    } else {
      this.subscribeToSaveResponse(this.recolectorTarifaService.create(recolectorTarifa));
    }
  }

  private createFromForm(): IRecolectorTarifa {
    return {
      ...new RecolectorTarifa(),
      id: this.editForm.get(['id']).value,
      rangoMinimo: this.editForm.get(['rangoMinimo']).value,
      rangoMaximo: this.editForm.get(['rangoMaximo']).value,
      precio: this.editForm.get(['precio']).value,
      recolectorId: this.editForm.get(['recolectorId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecolectorTarifa>>) {
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

  trackRecolectorById(index: number, item: IRecolector) {
    return item.id;
  }
}
