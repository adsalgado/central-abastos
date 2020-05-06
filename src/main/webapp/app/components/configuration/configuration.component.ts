import { Component, OnInit, Input, OnDestroy } from '@angular/core';

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  styleUrls: ['./configuration.component.scss']
})
export class ConfigurationComponent implements OnInit {
  public mostrarSide: boolean = false;

  constructor() {}

  ngOnInit() {}

  show() {
    this.mostrarSide = !this.mostrarSide;
  }
}
