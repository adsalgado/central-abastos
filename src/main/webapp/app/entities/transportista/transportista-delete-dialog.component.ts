import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransportista } from 'app/shared/model/transportista.model';
import { TransportistaService } from './transportista.service';

@Component({
  selector: 'jhi-transportista-delete-dialog',
  templateUrl: './transportista-delete-dialog.component.html'
})
export class TransportistaDeleteDialogComponent {
  transportista: ITransportista;

  constructor(
    protected transportistaService: TransportistaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.transportistaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'transportistaListModification',
        content: 'Deleted an transportista'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-transportista-delete-popup',
  template: ''
})
export class TransportistaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transportista }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TransportistaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.transportista = transportista;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/transportista', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/transportista', { outlets: { popup: null } }]);
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
