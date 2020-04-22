import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICarritoHistorico, CarritoHistorico } from 'app/shared/model/carrito-historico.model';
import { CarritoHistoricoService } from './carrito-historico.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';

@Component({
  selector: 'jhi-carrito-historico-update',
  templateUrl: './carrito-historico-update.component.html'
})
export class CarritoHistoricoUpdateComponent implements OnInit {
  isSaving: boolean;

  clientes: ICliente[];
  fechaAltaDp: any;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.maxLength(128)]],
    fechaAlta: [null, [Validators.required]],
    clienteId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected carritoHistoricoService: CarritoHistoricoService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ carritoHistorico }) => {
      this.updateForm(carritoHistorico);
    });
    this.clienteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICliente[]>) => response.body)
      )
      .subscribe((res: ICliente[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(carritoHistorico: ICarritoHistorico) {
    this.editForm.patchValue({
      id: carritoHistorico.id,
      nombre: carritoHistorico.nombre,
      fechaAlta: carritoHistorico.fechaAlta,
      clienteId: carritoHistorico.clienteId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const carritoHistorico = this.createFromForm();
    if (carritoHistorico.id !== undefined) {
      this.subscribeToSaveResponse(this.carritoHistoricoService.update(carritoHistorico));
    } else {
      this.subscribeToSaveResponse(this.carritoHistoricoService.create(carritoHistorico));
    }
  }

  private createFromForm(): ICarritoHistorico {
    return {
      ...new CarritoHistorico(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      fechaAlta: this.editForm.get(['fechaAlta']).value,
      clienteId: this.editForm.get(['clienteId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarritoHistorico>>) {
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
