import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecolectorTarifa } from 'app/shared/model/recolector-tarifa.model';
import { RecolectorTarifaService } from './recolector-tarifa.service';

@Component({
  selector: 'jhi-recolector-tarifa-delete-dialog',
  templateUrl: './recolector-tarifa-delete-dialog.component.html'
})
export class RecolectorTarifaDeleteDialogComponent {
  recolectorTarifa: IRecolectorTarifa;

  constructor(
    protected recolectorTarifaService: RecolectorTarifaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.recolectorTarifaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'recolectorTarifaListModification',
        content: 'Deleted an recolectorTarifa'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-recolector-tarifa-delete-popup',
  template: ''
})
export class RecolectorTarifaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ recolectorTarifa }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RecolectorTarifaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.recolectorTarifa = recolectorTarifa;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/main/entities/recolector-tarifa']);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/main/entities/recolector-tarifa']);
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
