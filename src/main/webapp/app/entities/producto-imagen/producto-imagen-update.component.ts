import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IProductoImagen, ProductoImagen } from 'app/shared/model/producto-imagen.model';
import { ProductoImagenService } from './producto-imagen.service';
import { IUser, UserService } from 'app/core';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { IAdjunto } from 'app/shared/model/adjunto.model';
import { AdjuntoService } from 'app/entities/adjunto';

@Component({
  selector: 'jhi-producto-imagen-update',
  templateUrl: './producto-imagen-update.component.html'
})
export class ProductoImagenUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  productos: IProducto[];

  adjuntos: IAdjunto[];

  editForm = this.fb.group({
    id: [],
    fechaAlta: [],
    usuarioAltaId: [],
    productoId: [],
    adjuntoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productoImagenService: ProductoImagenService,
    protected userService: UserService,
    protected productoService: ProductoService,
    protected adjuntoService: AdjuntoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productoImagen }) => {
      this.updateForm(productoImagen);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.productoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProducto[]>) => response.body)
      )
      .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.adjuntoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAdjunto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAdjunto[]>) => response.body)
      )
      .subscribe((res: IAdjunto[]) => (this.adjuntos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(productoImagen: IProductoImagen) {
    this.editForm.patchValue({
      id: productoImagen.id,
      fechaAlta: productoImagen.fechaAlta != null ? productoImagen.fechaAlta.format(DATE_TIME_FORMAT) : null,
      usuarioAltaId: productoImagen.usuarioAltaId,
      productoId: productoImagen.productoId,
      adjuntoId: productoImagen.adjuntoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productoImagen = this.createFromForm();
    if (productoImagen.id !== undefined) {
      this.subscribeToSaveResponse(this.productoImagenService.update(productoImagen));
    } else {
      this.subscribeToSaveResponse(this.productoImagenService.create(productoImagen));
    }
  }

  private createFromForm(): IProductoImagen {
    return {
      ...new ProductoImagen(),
      id: this.editForm.get(['id']).value,
      fechaAlta:
        this.editForm.get(['fechaAlta']).value != null ? moment(this.editForm.get(['fechaAlta']).value, DATE_TIME_FORMAT) : undefined,
      usuarioAltaId: this.editForm.get(['usuarioAltaId']).value,
      productoId: this.editForm.get(['productoId']).value,
      adjuntoId: this.editForm.get(['adjuntoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductoImagen>>) {
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

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }

  trackAdjuntoById(index: number, item: IAdjunto) {
    return item.id;
  }
}
