import { Component, OnInit, Inject, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { Queja } from 'app/shared/model/queja.model';

@Component({
  selector: 'app-reclamo-dialog',
  templateUrl: './reclamo-dialog.component.html',
  styleUrls: ['./reclamo-dialog.component.scss']
  // changeDetection: ChangeDetectionStrategy.OnPush
})
export class ReclamoDialogComponent {
  isVisible: Boolean = false;
  claim: any = null;
  constructor(@Inject(DOCUMENT) private document: Document) {}

  open(reclamo: any) {
    this.claim = reclamo;
    this.document.body.classList.add('modal-open');
    this.isVisible = true;
  }

  close() {
    this.isVisible = false;
    this.document.body.classList.remove('modal-open');
  }
}
