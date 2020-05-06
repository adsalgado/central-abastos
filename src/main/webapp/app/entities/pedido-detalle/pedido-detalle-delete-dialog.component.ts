import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPedidoDetalle } from 'app/shared/model/pedido-detalle.model';
import { PedidoDetalleService } from './pedido-detalle.service';

@Component({
  selector: 'jhi-pedido-detalle-delete-dialog',
  templateUrl: './pedido-detalle-delete-dialog.component.html'
})
export class PedidoDetalleDeleteDialogComponent {
  pedidoDetalle: IPedidoDetalle;

  constructor(
    protected pedidoDetalleService: PedidoDetalleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pedidoDetalleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pedidoDetalleListModification',
        content: 'Deleted an pedidoDetalle'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pedido-detalle-delete-popup',
  template: ''
})
export class PedidoDetalleDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pedidoDetalle }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PedidoDetalleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pedidoDetalle = pedidoDetalle;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/main/entities/pedido-detalle']);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/main/entities/pedido-detalle']);
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
