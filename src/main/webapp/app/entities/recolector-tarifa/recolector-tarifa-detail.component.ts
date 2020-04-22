import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecolectorTarifa } from 'app/shared/model/recolector-tarifa.model';

@Component({
  selector: 'jhi-recolector-tarifa-detail',
  templateUrl: './recolector-tarifa-detail.component.html'
})
export class RecolectorTarifaDetailComponent implements OnInit {
  recolectorTarifa: IRecolectorTarifa;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ recolectorTarifa }) => {
      this.recolectorTarifa = recolectorTarifa;
    });
  }

  previousState() {
    window.history.back();
  }
}
