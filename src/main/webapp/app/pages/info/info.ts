import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { GenericService } from '../../services/generic.service';

@Component({
  selector: 'page-info',
  templateUrl: 'info.html',
  styleUrls: ['./info.scss']
})
export class InfoPage {
  constructor(private genericService: GenericService) {}

  ionViewDidLoad() {}
}
