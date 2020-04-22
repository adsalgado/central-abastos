import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPagos } from 'app/shared/model/pagos.model';
import { PagosService } from './pagos.service';

@Component({
  selector: 'jhi-pagos-delete-dialog',
  templateUrl: './pagos-delete-dialog.component.html'
})
export class PagosDeleteDialogComponent {
  pagos: IPagos;

  constructor(protected pagosService: PagosService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pagosService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pagosListModification',
        content: 'Deleted an pagos'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pagos-delete-popup',
  template: ''
})
export class PagosDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pagos }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PagosDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pagos = pagos;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pagos', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pagos', { outlets: { popup: null } }]);
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
