import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICarritoHistoricoDetalle } from 'app/shared/model/carrito-historico-detalle.model';
import { CarritoHistoricoDetalleService } from './carrito-historico-detalle.service';

@Component({
  selector: 'jhi-carrito-historico-detalle-delete-dialog',
  templateUrl: './carrito-historico-detalle-delete-dialog.component.html'
})
export class CarritoHistoricoDetalleDeleteDialogComponent {
  carritoHistoricoDetalle: ICarritoHistoricoDetalle;

  constructor(
    protected carritoHistoricoDetalleService: CarritoHistoricoDetalleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.carritoHistoricoDetalleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'carritoHistoricoDetalleListModification',
        content: 'Deleted an carritoHistoricoDetalle'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-carrito-historico-detalle-delete-popup',
  template: ''
})
export class CarritoHistoricoDetalleDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ carritoHistoricoDetalle }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CarritoHistoricoDetalleDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.carritoHistoricoDetalle = carritoHistoricoDetalle;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/main/entities/carrito-historico-detalle']);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/main/entities/carrito-historico-detalle']);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
