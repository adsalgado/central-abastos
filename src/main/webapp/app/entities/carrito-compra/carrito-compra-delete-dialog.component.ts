import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICarritoCompra } from 'app/shared/model/carrito-compra.model';
import { CarritoCompraService } from './carrito-compra.service';

@Component({
  selector: 'jhi-carrito-compra-delete-dialog',
  templateUrl: './carrito-compra-delete-dialog.component.html'
})
export class CarritoCompraDeleteDialogComponent {
  carritoCompra: ICarritoCompra;

  constructor(
    protected carritoCompraService: CarritoCompraService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.carritoCompraService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'carritoCompraListModification',
        content: 'Deleted an carritoCompra'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-carrito-compra-delete-popup',
  template: ''
})
export class CarritoCompraDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ carritoCompra }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CarritoCompraDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.carritoCompra = carritoCompra;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/carrito-compra', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/carrito-compra', { outlets: { popup: null } }]);
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
