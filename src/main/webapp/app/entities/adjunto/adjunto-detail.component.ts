import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAdjunto } from 'app/shared/model/adjunto.model';

@Component({
  selector: 'jhi-adjunto-detail',
  templateUrl: './adjunto-detail.component.html'
})
export class AdjuntoDetailComponent implements OnInit {
  adjunto: IAdjunto;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ adjunto }) => {
      this.adjunto = adjunto;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
