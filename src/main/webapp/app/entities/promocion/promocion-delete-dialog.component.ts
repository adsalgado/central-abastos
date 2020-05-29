import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPromocion } from 'app/shared/model/promocion.model';
import { PromocionService } from './promocion.service';

@Component({
  selector: 'jhi-promocion-delete-dialog',
  templateUrl: './promocion-delete-dialog.component.html'
})
export class PromocionDeleteDialogComponent {
  promocion: IPromocion;

  constructor(protected promocionService: PromocionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.promocionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'promocionListModification',
        content: 'Deleted an promocion'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-promocion-delete-popup',
  template: ''
})
export class PromocionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ promocion }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PromocionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.promocion = promocion;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/main/entities/promocion']);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/main/entities/promocion']);
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
