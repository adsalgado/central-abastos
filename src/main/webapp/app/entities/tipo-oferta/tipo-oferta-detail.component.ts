import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoOferta } from 'app/shared/model/tipo-oferta.model';

@Component({
  selector: 'jhi-tipo-oferta-detail',
  templateUrl: './tipo-oferta-detail.component.html'
})
export class TipoOfertaDetailComponent implements OnInit {
  tipoOferta: ITipoOferta;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoOferta }) => {
      this.tipoOferta = tipoOferta;
    });
  }

  previousState() {
    window.history.back();
  }
}
