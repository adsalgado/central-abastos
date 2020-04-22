import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventarioHistorico } from 'app/shared/model/inventario-historico.model';

@Component({
  selector: 'jhi-inventario-historico-detail',
  templateUrl: './inventario-historico-detail.component.html'
})
export class InventarioHistoricoDetailComponent implements OnInit {
  inventarioHistorico: IInventarioHistorico;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ inventarioHistorico }) => {
      this.inventarioHistorico = inventarioHistorico;
    });
  }

  previousState() {
    window.history.back();
  }
}
