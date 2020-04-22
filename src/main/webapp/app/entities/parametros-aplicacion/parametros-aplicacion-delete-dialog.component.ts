import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParametrosAplicacion } from 'app/shared/model/parametros-aplicacion.model';
import { ParametrosAplicacionService } from './parametros-aplicacion.service';

@Component({
  selector: 'jhi-parametros-aplicacion-delete-dialog',
  templateUrl: './parametros-aplicacion-delete-dialog.component.html'
})
export class ParametrosAplicacionDeleteDialogComponent {
  parametrosAplicacion: IParametrosAplicacion;

  constructor(
    protected parametrosAplicacionService: ParametrosAplicacionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.parametrosAplicacionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'parametrosAplicacionListModification',
        content: 'Deleted an parametrosAplicacion'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-parametros-aplicacion-delete-popup',
  template: ''
})
export class ParametrosAplicacionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ parametrosAplicacion }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ParametrosAplicacionDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.parametrosAplicacion = parametrosAplicacion;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/parametros-aplicacion', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/parametros-aplicacion', { outlets: { popup: null } }]);
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
