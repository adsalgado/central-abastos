import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstatus } from 'app/shared/model/estatus.model';
import { EstatusService } from './estatus.service';

@Component({
  selector: 'jhi-estatus-delete-dialog',
  templateUrl: './estatus-delete-dialog.component.html'
})
export class EstatusDeleteDialogComponent {
  estatus: IEstatus;

  constructor(protected estatusService: EstatusService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.estatusService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'estatusListModification',
        content: 'Deleted an estatus'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-estatus-delete-popup',
  template: ''
})
export class EstatusDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estatus }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EstatusDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.estatus = estatus;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/estatus', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/estatus', { outlets: { popup: null } }]);
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
