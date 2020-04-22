import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUsuarioImagen } from 'app/shared/model/usuario-imagen.model';

@Component({
  selector: 'jhi-usuario-imagen-detail',
  templateUrl: './usuario-imagen-detail.component.html'
})
export class UsuarioImagenDetailComponent implements OnInit {
  usuarioImagen: IUsuarioImagen;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ usuarioImagen }) => {
      this.usuarioImagen = usuarioImagen;
    });
  }

  previousState() {
    window.history.back();
  }
}
