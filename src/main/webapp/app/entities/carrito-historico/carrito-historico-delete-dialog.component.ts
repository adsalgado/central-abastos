import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICarritoHistorico } from 'app/shared/model/carrito-historico.model';
import { CarritoHistoricoService } from './carrito-historico.service';

@Component({
  selector: 'jhi-carrito-historico-delete-dialog',
  templateUrl: './carrito-historico-delete-dialog.component.html'
})
export class CarritoHistoricoDeleteDialogComponent {
  carritoHistorico: ICarritoHistorico;

  constructor(
    protected carritoHistoricoService: CarritoHistoricoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.carritoHistoricoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'carritoHistoricoListModification',
        content: 'Deleted an carritoHistorico'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-carrito-historico-delete-popup',
  template: ''
})
export class CarritoHistoricoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ carritoHistorico }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CarritoHistoricoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.carritoHistorico = carritoHistorico;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/main/entities/carrito-historico']);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/main/entities/carrito-historico']);
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
