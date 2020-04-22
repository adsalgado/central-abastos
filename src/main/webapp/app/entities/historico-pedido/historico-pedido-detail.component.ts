import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHistoricoPedido } from 'app/shared/model/historico-pedido.model';

@Component({
  selector: 'jhi-historico-pedido-detail',
  templateUrl: './historico-pedido-detail.component.html'
})
export class HistoricoPedidoDetailComponent implements OnInit {
  historicoPedido: IHistoricoPedido;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ historicoPedido }) => {
      this.historicoPedido = historicoPedido;
    });
  }

  previousState() {
    window.history.back();
  }
}
