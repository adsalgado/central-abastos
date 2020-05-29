import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { environment } from '../../../environments/environment.prod';

import { IProductoProveedor } from 'app/shared/model/producto-proveedor.model';

@Component({
  selector: 'jhi-producto-proveedor-detail',
  templateUrl: './producto-proveedor-detail.component.html'
})
export class ProductoProveedorDetailComponent implements OnInit {
  productoProveedor: IProductoProveedor;
  public env: any = environment;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productoProveedor }) => {
      this.productoProveedor = productoProveedor;
    });
  }

  previousState() {
    window.history.back();
  }
}
