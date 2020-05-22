import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { GenericService } from '../../services/generic.service';
import { NavParamsService } from 'app/services/nav-params.service';
import { AlertService } from 'app/services/alert.service';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';

@Component({
  selector: 'page-problemas-pedido',
  templateUrl: 'problemas-pedido.html',
  styleUrls: ['./problemas-pedido.scss']
})
export class ProblemasPedidoPage {
  constructor(
    public navCtrl: NavParamsService,
    private genericService: GenericService,
    private localStorageEncryptService: LocalStorageEncryptService,
    private alertaService: AlertService
  ) {}

  ionViewDidLoad() {}
}
