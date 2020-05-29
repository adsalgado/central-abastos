import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { environment } from '../../../environments/environment.prod';

import { IPromocion } from 'app/shared/model/promocion.model';

@Component({
  selector: 'jhi-promocion-detail',
  templateUrl: './promocion-detail.component.html'
})
export class PromocionDetailComponent implements OnInit {
  promocion: IPromocion;
  public env: any = environment;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ promocion }) => {
      this.promocion = promocion;
    });
  }

  previousState() {
    window.history.back();
  }
}
