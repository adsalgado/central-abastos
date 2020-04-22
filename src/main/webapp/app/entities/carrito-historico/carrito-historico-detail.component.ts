import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICarritoHistorico } from 'app/shared/model/carrito-historico.model';

@Component({
  selector: 'jhi-carrito-historico-detail',
  templateUrl: './carrito-historico-detail.component.html'
})
export class CarritoHistoricoDetailComponent implements OnInit {
  carritoHistorico: ICarritoHistorico;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ carritoHistorico }) => {
      this.carritoHistorico = carritoHistorico;
    });
  }

  previousState() {
    window.history.back();
  }
}
