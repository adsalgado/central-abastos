import { GenericService } from './../../services/generic.service';
import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';

@Component({
  selector: 'page-terminos-condiciones',
  templateUrl: 'terminos-condiciones.html',
  styleUrls: ['./terminos-condiciones.scss']
})
export class TerminosCondicionesPage {
  constructor(private genericService: GenericService) {}

  ionViewDidLoad() {}
}
