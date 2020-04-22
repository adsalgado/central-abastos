import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQueja } from 'app/shared/model/queja.model';

@Component({
  selector: 'jhi-queja-detail',
  templateUrl: './queja-detail.component.html'
})
export class QuejaDetailComponent implements OnInit {
  queja: IQueja;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ queja }) => {
      this.queja = queja;
    });
  }

  previousState() {
    window.history.back();
  }
}
