import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductoImagen } from 'app/shared/model/producto-imagen.model';

@Component({
  selector: 'jhi-producto-imagen-detail',
  templateUrl: './producto-imagen-detail.component.html'
})
export class ProductoImagenDetailComponent implements OnInit {
  productoImagen: IProductoImagen;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productoImagen }) => {
      this.productoImagen = productoImagen;
    });
  }

  previousState() {
    window.history.back();
  }
}
