import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransportistaTarifa } from 'app/shared/model/transportista-tarifa.model';
import { TransportistaTarifaService } from './transportista-tarifa.service';

@Component({
  selector: 'jhi-transportista-tarifa-delete-dialog',
  templateUrl: './transportista-tarifa-delete-dialog.component.html'
})
export class TransportistaTarifaDeleteDialogComponent {
  transportistaTarifa: ITransportistaTarifa;

  constructor(
    protected transportistaTarifaService: TransportistaTarifaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.transportistaTarifaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'transportistaTarifaListModification',
        content: 'Deleted an transportistaTarifa'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-transportista-tarifa-delete-popup',
  template: ''
})
export class TransportistaTarifaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transportistaTarifa }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TransportistaTarifaDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.transportistaTarifa = transportistaTarifa;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/main/entities/transportista-tarifa']);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/main/entities/transportista-tarifa']);
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
