import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstatus } from 'app/shared/model/estatus.model';

@Component({
  selector: 'jhi-estatus-detail',
  templateUrl: './estatus-detail.component.html'
})
export class EstatusDetailComponent implements OnInit {
  estatus: IEstatus;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estatus }) => {
      this.estatus = estatus;
    });
  }

  previousState() {
    window.history.back();
  }
}
