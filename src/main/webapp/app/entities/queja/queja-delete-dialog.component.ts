import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQueja } from 'app/shared/model/queja.model';
import { QuejaService } from './queja.service';

@Component({
  selector: 'jhi-queja-delete-dialog',
  templateUrl: './queja-delete-dialog.component.html'
})
export class QuejaDeleteDialogComponent {
  queja: IQueja;

  constructor(protected quejaService: QuejaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.quejaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'quejaListModification',
        content: 'Deleted an queja'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-queja-delete-popup',
  template: ''
})
export class QuejaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ queja }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(QuejaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.queja = queja;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/main/entities/queja']);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/main/entities/queja']);
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
