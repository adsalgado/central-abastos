import { environment } from '../../../environments/environment.prod';
import { LoadingService } from 'app/services/loading-service';
import { GenericService } from './../../services/generic.service';
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
import { SelectItem } from 'primeng/components/common/selectitem';
import { MessageService } from 'primeng/api';
import { ReporteCostosRequest } from 'app/shared/model/reporte-costos-request.model';

@Component({
  selector: 'jhi-reporte-costos',
  templateUrl: './reporte-costos.component.html'
})
export class ReporteCostosComponent implements OnInit {
  isSaving: boolean;
  transportistas: ITransportista[];
  proveedores: IProveedor[];
  user: User = null;
  submitted: boolean = false;

  editForm = this.fb.group({
    proveedorId: [Validators.required],
    fechaInicial: [],
    fechaFinal: []
  });

  public dataReponse: any = null;

  constructor(
    protected jhiAlertService: JhiAlertService,
    private localStorageEncryptService: LocalStorageEncryptService,
    protected proveedorService: ProveedorService,
    protected userService: UserService,
    protected transportistaService: TransportistaService,
    protected activatedRoute: ActivatedRoute,
    protected messageService: MessageService,
    private fb: FormBuilder,
    private genericService: GenericService,
    private loadingService: LoadingService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.user = this.localStorageEncryptService.getFromLocalStorage(`userSession`);
    console.log(this.user);

    this.proveedorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITransportista[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITransportista[]>) => response.body)
      )
      .subscribe((res: IProveedor[]) => (this.proveedores = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  onSubmit(value: string) {
    this.submitted = true;
    this.messageService.add({ severity: 'info', summary: 'Success', detail: 'Form Submitted' });
  }

  previousState() {
    window.history.back();
  }

  save() {
    let selectedProveedor = this.editForm.get(['proveedorId']).value;
    if (!selectedProveedor.id) {
      this.messageService.add({ severity: 'error', summary: 'Success', detail: 'El proveedor es requerido.' });
    }
    let reporteCostosRequest: any = {
      claveReporte: 'REPORTE_COSTOS',
      proveedorId: selectedProveedor.id,
      fechaInicial: this.editForm.get(['fechaInicial']).value
        ? moment(this.editForm.get(['fechaInicial']).value).format('YYYY/MM/DD')
        : null,
      fechaFinal: this.editForm.get(['fechaFinal']).value ? moment(this.editForm.get(['fechaFinal']).value).format('YYYY/MM/DD') : null
    };

    this.loadingService.show();
    this.genericService.sendPostRequest(`${environment.reportes}`, reporteCostosRequest).subscribe(
      (response: any) => {
        this.loadingService.hide();
        console.log(response);
        if (response.error) {
          this.messageService.add({ severity: 'error', summary: 'Success', detail: `${response.messageError}` });
        } else {
          this.dataReponse = response.data;
        }
        console.log(this.dataReponse);
      },
      (error: HttpErrorResponse) => {
        this.loadingService.hide();
        this.dataReponse = null;
        this.messageService.add({
          severity: 'error',
          summary: 'Success',
          detail: 'No se ha podido obtener tu reporte, intenta nuevamente'
        });
      }
    );
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
