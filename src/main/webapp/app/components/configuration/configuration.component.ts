import { Component, OnInit, Input, OnDestroy } from '@angular/core';

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  styleUrls: ['./configuration.component.scss']
})
export class ConfigurationComponent implements OnInit, OnDestroy {
  public mostrarSide: boolean = false;

  constructor() {}

  ngOnInit() {}

  ngOnDestroy(): void {
    throw new Error('Method not implemented.');
  }

  show() {
    this.mostrarSide = !this.mostrarSide;
  }
}
