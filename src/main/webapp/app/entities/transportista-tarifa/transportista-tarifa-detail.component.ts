import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransportistaTarifa } from 'app/shared/model/transportista-tarifa.model';

@Component({
  selector: 'jhi-transportista-tarifa-detail',
  templateUrl: './transportista-tarifa-detail.component.html'
})
export class TransportistaTarifaDetailComponent implements OnInit {
  transportistaTarifa: ITransportistaTarifa;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transportistaTarifa }) => {
      this.transportistaTarifa = transportistaTarifa;
    });
  }

  previousState() {
    window.history.back();
  }
}
