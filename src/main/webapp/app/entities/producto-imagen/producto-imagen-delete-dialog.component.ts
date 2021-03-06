import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductoImagen } from 'app/shared/model/producto-imagen.model';
import { ProductoImagenService } from './producto-imagen.service';

@Component({
  selector: 'jhi-producto-imagen-delete-dialog',
  templateUrl: './producto-imagen-delete-dialog.component.html'
})
export class ProductoImagenDeleteDialogComponent {
  productoImagen: IProductoImagen;

  constructor(
    protected productoImagenService: ProductoImagenService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productoImagenService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productoImagenListModification',
        content: 'Deleted an productoImagen'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-producto-imagen-delete-popup',
  template: ''
})
export class ProductoImagenDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;
  productoProveedorId: number;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    let sId = this.activatedRoute.snapshot.paramMap.get('productoProveedorId');
    if (!isNaN(Number(sId))) {
      this.productoProveedorId = Number(sId);
    } else {
      console.log('Not a Number');
    }

    this.activatedRoute.data.subscribe(({ productoImagen }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductoImagenDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productoImagen = productoImagen;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/main/entities/producto-imagen/producto-proveedor', this.productoProveedorId]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/main/entities/producto-imagen/producto-proveedor', this.productoProveedorId]);
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
