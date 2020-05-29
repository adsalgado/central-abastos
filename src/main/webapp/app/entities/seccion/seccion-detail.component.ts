import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { environment } from '../../../environments/environment.prod';

import { ISeccion } from 'app/shared/model/seccion.model';

@Component({
  selector: 'jhi-seccion-detail',
  templateUrl: './seccion-detail.component.html'
})
export class SeccionDetailComponent implements OnInit {
  seccion: ISeccion;
  public env: any = environment;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ seccion }) => {
      this.seccion = seccion;
    });
  }

  previousState() {
    window.history.back();
  }
}
