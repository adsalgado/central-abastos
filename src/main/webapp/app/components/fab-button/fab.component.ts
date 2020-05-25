import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'fabi-button',
  templateUrl: './fab.component.html',
  styleUrls: ['./fab.component.scss']
})
export class FabComponent implements OnInit {
  @Input() styles;
  public id: any;

  /** Generate FabComponent */
  constructor() {}

  /** init pages */
  ngOnInit(): void {
    this.id = Math.round(new Date().getTime() / 1000) * (Math.random() * (1000 - 1) + 1);
    let miBoton: any = document.getElementById(`${this.id}`);
  }
}
