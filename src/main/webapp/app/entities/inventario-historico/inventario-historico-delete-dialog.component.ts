import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInventarioHistorico } from 'app/shared/model/inventario-historico.model';
import { InventarioHistoricoService } from './inventario-historico.service';

@Component({
  selector: 'jhi-inventario-historico-delete-dialog',
  templateUrl: './inventario-historico-delete-dialog.component.html'
})
export class InventarioHistoricoDeleteDialogComponent {
  inventarioHistorico: IInventarioHistorico;

  constructor(
    protected inventarioHistoricoService: InventarioHistoricoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.inventarioHistoricoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'inventarioHistoricoListModification',
        content: 'Deleted an inventarioHistorico'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-inventario-historico-delete-popup',
  template: ''
})
export class InventarioHistoricoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ inventarioHistorico }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InventarioHistoricoDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.inventarioHistorico = inventarioHistorico;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/inventario-historico', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/inventario-historico', { outlets: { popup: null } }]);
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
