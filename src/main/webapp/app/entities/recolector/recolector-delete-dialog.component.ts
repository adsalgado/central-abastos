import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecolector } from 'app/shared/model/recolector.model';
import { RecolectorService } from './recolector.service';

@Component({
  selector: 'jhi-recolector-delete-dialog',
  templateUrl: './recolector-delete-dialog.component.html'
})
export class RecolectorDeleteDialogComponent {
  recolector: IRecolector;

  constructor(
    protected recolectorService: RecolectorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.recolectorService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'recolectorListModification',
        content: 'Deleted an recolector'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-recolector-delete-popup',
  template: ''
})
export class RecolectorDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ recolector }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RecolectorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.recolector = recolector;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/recolector', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/recolector', { outlets: { popup: null } }]);
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
