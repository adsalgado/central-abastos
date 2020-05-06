import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUsuarioImagen } from 'app/shared/model/usuario-imagen.model';
import { UsuarioImagenService } from './usuario-imagen.service';

@Component({
  selector: 'jhi-usuario-imagen-delete-dialog',
  templateUrl: './usuario-imagen-delete-dialog.component.html'
})
export class UsuarioImagenDeleteDialogComponent {
  usuarioImagen: IUsuarioImagen;

  constructor(
    protected usuarioImagenService: UsuarioImagenService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.usuarioImagenService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'usuarioImagenListModification',
        content: 'Deleted an usuarioImagen'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-usuario-imagen-delete-popup',
  template: ''
})
export class UsuarioImagenDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ usuarioImagen }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UsuarioImagenDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.usuarioImagen = usuarioImagen;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/main/entities/usuario-imagen']);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/main/entities/usuario-imagen']);
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
