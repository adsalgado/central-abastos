import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ITarjeta, Tarjeta } from 'app/shared/model/tarjeta.model';
import { TarjetaService } from './tarjeta.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';

@Component({
  selector: 'jhi-tarjeta-update',
  templateUrl: './tarjeta-update.component.html'
})
export class TarjetaUpdateComponent implements OnInit {
  isSaving: boolean;

  clientes: ICliente[];
  fechaAltaDp: any;

  editForm = this.fb.group({
    id: [],
    numeroTarjeta: [null, [Validators.required, Validators.maxLength(20)]],
    fechaCaducidad: [null, [Validators.required, Validators.maxLength(10)]],
    numeroSeguridad: [null, [Validators.required, Validators.maxLength(3)]],
    fechaAlta: [null, [Validators.required]],
    clienteId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tarjetaService: TarjetaService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tarjeta }) => {
      this.updateForm(tarjeta);
    });
    this.clienteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICliente[]>) => response.body)
      )
      .subscribe((res: ICliente[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(tarjeta: ITarjeta) {
    this.editForm.patchValue({
      id: tarjeta.id,
      numeroTarjeta: tarjeta.numeroTarjeta,
      fechaCaducidad: tarjeta.fechaCaducidad,
      numeroSeguridad: tarjeta.numeroSeguridad,
      fechaAlta: tarjeta.fechaAlta,
      clienteId: tarjeta.clienteId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tarjeta = this.createFromForm();
    if (tarjeta.id !== undefined) {
      this.subscribeToSaveResponse(this.tarjetaService.update(tarjeta));
    } else {
      this.subscribeToSaveResponse(this.tarjetaService.create(tarjeta));
    }
  }

  private createFromForm(): ITarjeta {
    return {
      ...new Tarjeta(),
      id: this.editForm.get(['id']).value,
      numeroTarjeta: this.editForm.get(['numeroTarjeta']).value,
      fechaCaducidad: this.editForm.get(['fechaCaducidad']).value,
      numeroSeguridad: this.editForm.get(['numeroSeguridad']).value,
      fechaAlta: this.editForm.get(['fechaAlta']).value,
      clienteId: this.editForm.get(['clienteId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarjeta>>) {
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

  trackClienteById(index: number, item: ICliente) {
    return item.id;
  }
}
