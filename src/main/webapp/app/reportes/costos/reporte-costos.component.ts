import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IProveedor, Proveedor } from 'app/shared/model/proveedor.model';
import { IUser, UserService } from 'app/core';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from '../../entities/empresa';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
import { User } from '../../models/User';
import { ITransportista } from 'app/shared/model/transportista.model';
import { ProveedorService } from '../../entities/proveedor/proveedor.service';
import { TransportistaService } from '../../entities/transportista/transportista.service';

@Component({
  selector: 'jhi-reporte-costos',
  templateUrl: './reporte-costos.component.html'
})
export class ReporteCostosComponent implements OnInit {
  isSaving: boolean;
  transportistas: ITransportista[];
  user: User = null;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.maxLength(256)]],
    transportistaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    private localStorageEncryptService: LocalStorageEncryptService,
    protected proveedorService: ProveedorService,
    protected userService: UserService,
    protected transportistaService: TransportistaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.user = this.localStorageEncryptService.getFromLocalStorage(`userSession`);
    console.log(this.user);

    this.proveedorService
      .findByUserName(this.user.username)
      .pipe(
        filter((response: HttpResponse<Proveedor>) => response.ok),
        map((proveedor: HttpResponse<Proveedor>) => proveedor.body)
      )
      .subscribe(
        (res: IProveedor) => {
          this.updateForm(res);
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.transportistaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITransportista[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITransportista[]>) => response.body)
      )
      .subscribe((res: ITransportista[]) => (this.transportistas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(proveedor: IProveedor) {
    this.editForm.patchValue({
      id: proveedor.id,
      nombre: proveedor.nombre,
      transportistaId: proveedor.transportistaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const proveedor = this.createFromForm();
    if (proveedor.id !== undefined) {
      this.subscribeToSaveResponse(this.proveedorService.updateTransportistaProveedor(proveedor));
    } else {
      this.subscribeToSaveResponse(this.proveedorService.create(proveedor));
    }
  }

  private createFromForm(): IProveedor {
    return {
      ...new Proveedor(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      transportistaId: this.editForm.get(['transportistaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProveedor>>) {
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

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }
}
