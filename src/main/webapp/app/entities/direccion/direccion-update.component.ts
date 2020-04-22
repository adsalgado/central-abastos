import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDireccion, Direccion } from 'app/shared/model/direccion.model';
import { DireccionService } from './direccion.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';

@Component({
  selector: 'jhi-direccion-update',
  templateUrl: './direccion-update.component.html'
})
export class DireccionUpdateComponent implements OnInit {
  isSaving: boolean;

  clientes: ICliente[];

  editForm = this.fb.group({
    id: [],
    direccion: [null, [Validators.required, Validators.maxLength(256)]],
    colonia: [null, [Validators.maxLength(100)]],
    codigoPostal: [null, [Validators.maxLength(5)]],
    geolocalizacion: [null, [Validators.maxLength(128)]],
    clienteId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected direccionService: DireccionService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ direccion }) => {
      this.updateForm(direccion);
    });
    this.clienteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICliente[]>) => response.body)
      )
      .subscribe((res: ICliente[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(direccion: IDireccion) {
    this.editForm.patchValue({
      id: direccion.id,
      direccion: direccion.direccion,
      colonia: direccion.colonia,
      codigoPostal: direccion.codigoPostal,
      geolocalizacion: direccion.geolocalizacion,
      clienteId: direccion.clienteId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const direccion = this.createFromForm();
    if (direccion.id !== undefined) {
      this.subscribeToSaveResponse(this.direccionService.update(direccion));
    } else {
      this.subscribeToSaveResponse(this.direccionService.create(direccion));
    }
  }

  private createFromForm(): IDireccion {
    return {
      ...new Direccion(),
      id: this.editForm.get(['id']).value,
      direccion: this.editForm.get(['direccion']).value,
      colonia: this.editForm.get(['colonia']).value,
      codigoPostal: this.editForm.get(['codigoPostal']).value,
      geolocalizacion: this.editForm.get(['geolocalizacion']).value,
      clienteId: this.editForm.get(['clienteId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDireccion>>) {
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
