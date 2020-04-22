import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICarritoCompra } from 'app/shared/model/carrito-compra.model';

@Component({
  selector: 'jhi-carrito-compra-detail',
  templateUrl: './carrito-compra-detail.component.html'
})
export class CarritoCompraDetailComponent implements OnInit {
  carritoCompra: ICarritoCompra;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ carritoCompra }) => {
      this.carritoCompra = carritoCompra;
    });
  }

  previousState() {
    window.history.back();
  }
}
