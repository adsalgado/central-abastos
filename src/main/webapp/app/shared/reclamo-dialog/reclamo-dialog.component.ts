import { Component, OnInit, Inject, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { Queja } from '../model/queja.model';

@Component({
  selector: 'app-reclamo-dialog',
  templateUrl: './reclamo-dialog.component.html',
  styleUrls: ['./reclamo-dialog.component.scss']
  // changeDetection: ChangeDetectionStrategy.OnPush
})
export class ReclamoDialogComponent {
  isVisible: Boolean = false;
  claim: Queja = null;
  constructor(@Inject(DOCUMENT) private document: Document) {}

  open(reclamo: Queja) {
    this.claim = reclamo;
    this.document.body.classList.add('modal-open');
    this.isVisible = true;
  }

  close() {
    this.isVisible = false;
    this.document.body.classList.remove('modal-open');
  }
}
