import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoOferta } from 'app/shared/model/tipo-oferta.model';
import { TipoOfertaService } from './tipo-oferta.service';

@Component({
  selector: 'jhi-tipo-oferta-delete-dialog',
  templateUrl: './tipo-oferta-delete-dialog.component.html'
})
export class TipoOfertaDeleteDialogComponent {
  tipoOferta: ITipoOferta;

  constructor(
    protected tipoOfertaService: TipoOfertaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tipoOfertaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tipoOfertaListModification',
        content: 'Deleted an tipoOferta'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tipo-oferta-delete-popup',
  template: ''
})
export class TipoOfertaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoOferta }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TipoOfertaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tipoOferta = tipoOferta;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/main/entities/tipo-oferta']);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/main/entities/tipo-oferta']);
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
