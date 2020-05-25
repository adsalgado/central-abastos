import { GenericService } from './../../services/generic.service';
import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';

@Component({
  selector: 'page-acerca-de',
  templateUrl: 'acerca-de.html',
  styleUrls: ['./acerca-de.scss']
})
export class AcercaDePage {
  public version: any = '1.0.0';
  public anio: any = '2020';
  constructor(private genericService: GenericService) {}

  ionViewDidLoad() {
    this.anio = new Date().getFullYear();
  }
}
