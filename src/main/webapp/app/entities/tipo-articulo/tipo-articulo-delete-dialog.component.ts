import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoArticulo } from 'app/shared/model/tipo-articulo.model';
import { TipoArticuloService } from './tipo-articulo.service';

@Component({
  selector: 'jhi-tipo-articulo-delete-dialog',
  templateUrl: './tipo-articulo-delete-dialog.component.html'
})
export class TipoArticuloDeleteDialogComponent {
  tipoArticulo: ITipoArticulo;

  constructor(
    protected tipoArticuloService: TipoArticuloService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tipoArticuloService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tipoArticuloListModification',
        content: 'Deleted an tipoArticulo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tipo-articulo-delete-popup',
  template: ''
})
export class TipoArticuloDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoArticulo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TipoArticuloDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tipoArticulo = tipoArticulo;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/main/entities/tipo-articulo']);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/main/entities/tipo-articulo']);
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
