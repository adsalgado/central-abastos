import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { environment } from '../../../environments/environment.prod';

import { IProductoImagen } from 'app/shared/model/producto-imagen.model';

@Component({
  selector: 'jhi-producto-imagen-detail',
  templateUrl: './producto-imagen-detail.component.html'
})
export class ProductoImagenDetailComponent implements OnInit {
  productoImagen: IProductoImagen;
  productoProveedorId: number;
  public env: any = environment;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    let sId = this.activatedRoute.snapshot.paramMap.get('productoProveedorId');
    if (!isNaN(Number(sId))) {
      this.productoProveedorId = Number(sId);
    } else {
      console.log('Not a Number');
    }
    this.activatedRoute.data.subscribe(({ productoImagen }) => {
      this.productoImagen = productoImagen;
    });
  }

  previousState() {
    window.history.back();
  }
}
