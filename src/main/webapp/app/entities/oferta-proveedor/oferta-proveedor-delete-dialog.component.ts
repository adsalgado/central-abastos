import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOfertaProveedor } from 'app/shared/model/oferta-proveedor.model';
import { OfertaProveedorService } from './oferta-proveedor.service';

@Component({
  selector: 'jhi-oferta-proveedor-delete-dialog',
  templateUrl: './oferta-proveedor-delete-dialog.component.html'
})
export class OfertaProveedorDeleteDialogComponent {
  ofertaProveedor: IOfertaProveedor;

  constructor(
    protected ofertaProveedorService: OfertaProveedorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.ofertaProveedorService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'ofertaProveedorListModification',
        content: 'Deleted an ofertaProveedor'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-oferta-proveedor-delete-popup',
  template: ''
})
export class OfertaProveedorDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ofertaProveedor }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OfertaProveedorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.ofertaProveedor = ofertaProveedor;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/oferta-proveedor', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/oferta-proveedor', { outlets: { popup: null } }]);
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
