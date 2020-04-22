import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoArticulo } from 'app/shared/model/tipo-articulo.model';

@Component({
  selector: 'jhi-tipo-articulo-detail',
  templateUrl: './tipo-articulo-detail.component.html'
})
export class TipoArticuloDetailComponent implements OnInit {
  tipoArticulo: ITipoArticulo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoArticulo }) => {
      this.tipoArticulo = tipoArticulo;
    });
  }

  previousState() {
    window.history.back();
  }
}
