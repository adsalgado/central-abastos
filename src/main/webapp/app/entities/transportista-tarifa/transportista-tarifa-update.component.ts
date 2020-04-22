import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITransportistaTarifa, TransportistaTarifa } from 'app/shared/model/transportista-tarifa.model';
import { TransportistaTarifaService } from './transportista-tarifa.service';
import { ITransportista } from 'app/shared/model/transportista.model';
import { TransportistaService } from 'app/entities/transportista';

@Component({
  selector: 'jhi-transportista-tarifa-update',
  templateUrl: './transportista-tarifa-update.component.html'
})
export class TransportistaTarifaUpdateComponent implements OnInit {
  isSaving: boolean;

  transportistas: ITransportista[];

  editForm = this.fb.group({
    id: [],
    rangoMinimo: [null, [Validators.required]],
    rangoMaximo: [null, [Validators.required]],
    precio: [null, [Validators.required]],
    transportistaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected transportistaTarifaService: TransportistaTarifaService,
    protected transportistaService: TransportistaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transportistaTarifa }) => {
      this.updateForm(transportistaTarifa);
    });
    this.transportistaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITransportista[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITransportista[]>) => response.body)
      )
      .subscribe((res: ITransportista[]) => (this.transportistas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(transportistaTarifa: ITransportistaTarifa) {
    this.editForm.patchValue({
      id: transportistaTarifa.id,
      rangoMinimo: transportistaTarifa.rangoMinimo,
      rangoMaximo: transportistaTarifa.rangoMaximo,
      precio: transportistaTarifa.precio,
      transportistaId: transportistaTarifa.transportistaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transportistaTarifa = this.createFromForm();
    if (transportistaTarifa.id !== undefined) {
      this.subscribeToSaveResponse(this.transportistaTarifaService.update(transportistaTarifa));
    } else {
      this.subscribeToSaveResponse(this.transportistaTarifaService.create(transportistaTarifa));
    }
  }

  private createFromForm(): ITransportistaTarifa {
    return {
      ...new TransportistaTarifa(),
      id: this.editForm.get(['id']).value,
      rangoMinimo: this.editForm.get(['rangoMinimo']).value,
      rangoMaximo: this.editForm.get(['rangoMaximo']).value,
      precio: this.editForm.get(['precio']).value,
      transportistaId: this.editForm.get(['transportistaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransportistaTarifa>>) {
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

  trackTransportistaById(index: number, item: ITransportista) {
    return item.id;
  }
}
