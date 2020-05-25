import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductoProveedor } from 'app/shared/model/producto-proveedor.model';
import { ProductoProveedorService } from './producto-proveedor.service';

@Component({
  selector: 'jhi-producto-proveedor-delete-dialog',
  templateUrl: './producto-proveedor-delete-dialog.component.html'
})
export class ProductoProveedorDeleteDialogComponent {
  productoProveedor: IProductoProveedor;

  constructor(
    protected productoProveedorService: ProductoProveedorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productoProveedorService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productoProveedorListModification',
        content: 'Deleted an productoProveedor'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-producto-proveedor-delete-popup',
  template: ''
})
export class ProductoProveedorDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productoProveedor }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductoProveedorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productoProveedor = productoProveedor;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/main/entities/producto-proveedor']);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/main/entities/producto-proveedor']);
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
