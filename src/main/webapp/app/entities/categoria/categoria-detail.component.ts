import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { environment } from '../../../environments/environment.prod';

import { ICategoria } from 'app/shared/model/categoria.model';

@Component({
  selector: 'jhi-categoria-detail',
  templateUrl: './categoria-detail.component.html'
})
export class CategoriaDetailComponent implements OnInit {
  categoria: ICategoria;
  public env: any = environment;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ categoria }) => {
      this.categoria = categoria;
    });
  }

  previousState() {
    window.history.back();
  }
}
