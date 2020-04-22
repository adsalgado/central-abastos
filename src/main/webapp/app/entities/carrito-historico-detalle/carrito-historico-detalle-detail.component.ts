import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICarritoHistoricoDetalle } from 'app/shared/model/carrito-historico-detalle.model';

@Component({
  selector: 'jhi-carrito-historico-detalle-detail',
  templateUrl: './carrito-historico-detalle-detail.component.html'
})
export class CarritoHistoricoDetalleDetailComponent implements OnInit {
  carritoHistoricoDetalle: ICarritoHistoricoDetalle;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ carritoHistoricoDetalle }) => {
      this.carritoHistoricoDetalle = carritoHistoricoDetalle;
    });
  }

  previousState() {
    window.history.back();
  }
}
