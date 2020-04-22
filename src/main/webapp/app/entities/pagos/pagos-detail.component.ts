import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPagos } from 'app/shared/model/pagos.model';

@Component({
  selector: 'jhi-pagos-detail',
  templateUrl: './pagos-detail.component.html'
})
export class PagosDetailComponent implements OnInit {
  pagos: IPagos;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pagos }) => {
      this.pagos = pagos;
    });
  }

  previousState() {
    window.history.back();
  }
}
