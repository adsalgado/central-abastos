import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IInventarioHistorico, InventarioHistorico } from 'app/shared/model/inventario-historico.model';
import { InventarioHistoricoService } from './inventario-historico.service';
import { IUser, UserService } from 'app/core';
import { IInventario } from 'app/shared/model/inventario.model';
import { InventarioService } from 'app/entities/inventario';

@Component({
  selector: 'jhi-inventario-historico-update',
  templateUrl: './inventario-historico-update.component.html'
})
export class InventarioHistoricoUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  inventarios: IInventario[];

  editForm = this.fb.group({
    id: [],
    tipoMovimiento: [null, [Validators.required]],
    cantidad: [null, [Validators.required]],
    totalAnterior: [null, [Validators.required]],
    totalFinal: [null, [Validators.required]],
    fechaMovimiento: [null, [Validators.required]],
    usuarioMovimientoId: [],
    inventarioId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected inventarioHistoricoService: InventarioHistoricoService,
    protected userService: UserService,
    protected inventarioService: InventarioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ inventarioHistorico }) => {
      this.updateForm(inventarioHistorico);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.inventarioService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IInventario[]>) => mayBeOk.ok),
        map((response: HttpResponse<IInventario[]>) => response.body)
      )
      .subscribe((res: IInventario[]) => (this.inventarios = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(inventarioHistorico: IInventarioHistorico) {
    this.editForm.patchValue({
      id: inventarioHistorico.id,
      tipoMovimiento: inventarioHistorico.tipoMovimiento,
      cantidad: inventarioHistorico.cantidad,
      totalAnterior: inventarioHistorico.totalAnterior,
      totalFinal: inventarioHistorico.totalFinal,
      fechaMovimiento: inventarioHistorico.fechaMovimiento != null ? inventarioHistorico.fechaMovimiento.format(DATE_TIME_FORMAT) : null,
      usuarioMovimientoId: inventarioHistorico.usuarioMovimientoId,
      inventarioId: inventarioHistorico.inventarioId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const inventarioHistorico = this.createFromForm();
    if (inventarioHistorico.id !== undefined) {
      this.subscribeToSaveResponse(this.inventarioHistoricoService.update(inventarioHistorico));
    } else {
      this.subscribeToSaveResponse(this.inventarioHistoricoService.create(inventarioHistorico));
    }
  }

  private createFromForm(): IInventarioHistorico {
    return {
      ...new InventarioHistorico(),
      id: this.editForm.get(['id']).value,
      tipoMovimiento: this.editForm.get(['tipoMovimiento']).value,
      cantidad: this.editForm.get(['cantidad']).value,
      totalAnterior: this.editForm.get(['totalAnterior']).value,
      totalFinal: this.editForm.get(['totalFinal']).value,
      fechaMovimiento:
        this.editForm.get(['fechaMovimiento']).value != null
          ? moment(this.editForm.get(['fechaMovimiento']).value, DATE_TIME_FORMAT)
          : undefined,
      usuarioMovimientoId: this.editForm.get(['usuarioMovimientoId']).value,
      inventarioId: this.editForm.get(['inventarioId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventarioHistorico>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackInventarioById(index: number, item: IInventario) {
    return item.id;
  }
}
