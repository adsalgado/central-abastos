import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdjunto } from 'app/shared/model/adjunto.model';
import { AdjuntoService } from './adjunto.service';

@Component({
  selector: 'jhi-adjunto-delete-dialog',
  templateUrl: './adjunto-delete-dialog.component.html'
})
export class AdjuntoDeleteDialogComponent {
  adjunto: IAdjunto;

  constructor(protected adjuntoService: AdjuntoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.adjuntoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'adjuntoListModification',
        content: 'Deleted an adjunto'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-adjunto-delete-popup',
  template: ''
})
export class AdjuntoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ adjunto }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AdjuntoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.adjunto = adjunto;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/adjunto', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/adjunto', { outlets: { popup: null } }]);
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
