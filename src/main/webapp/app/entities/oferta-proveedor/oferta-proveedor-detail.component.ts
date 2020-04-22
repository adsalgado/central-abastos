import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOfertaProveedor } from 'app/shared/model/oferta-proveedor.model';

@Component({
  selector: 'jhi-oferta-proveedor-detail',
  templateUrl: './oferta-proveedor-detail.component.html'
})
export class OfertaProveedorDetailComponent implements OnInit {
  ofertaProveedor: IOfertaProveedor;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ofertaProveedor }) => {
      this.ofertaProveedor = ofertaProveedor;
    });
  }

  previousState() {
    window.history.back();
  }
}
