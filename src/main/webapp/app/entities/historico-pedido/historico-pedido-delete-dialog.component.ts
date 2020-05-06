import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHistoricoPedido } from 'app/shared/model/historico-pedido.model';
import { HistoricoPedidoService } from './historico-pedido.service';

@Component({
  selector: 'jhi-historico-pedido-delete-dialog',
  templateUrl: './historico-pedido-delete-dialog.component.html'
})
export class HistoricoPedidoDeleteDialogComponent {
  historicoPedido: IHistoricoPedido;

  constructor(
    protected historicoPedidoService: HistoricoPedidoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.historicoPedidoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'historicoPedidoListModification',
        content: 'Deleted an historicoPedido'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-historico-pedido-delete-popup',
  template: ''
})
export class HistoricoPedidoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ historicoPedido }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(HistoricoPedidoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.historicoPedido = historicoPedido;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/main/entities/historico-pedido']);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/main/entities/historico-pedido']);
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
