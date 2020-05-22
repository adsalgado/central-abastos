import { NavParamsService } from 'app/services/nav-params.service';
import { NavParams } from 'ionic-angular';
import { GenericService } from './../../services/generic.service';
import { Component, OnInit } from '@angular/core';
declare var QRCode;

@Component({
  selector: 'page-qr',
  templateUrl: 'qr.html',
  styleUrls: ['./qr.scss']
})
export class QrPage implements OnInit {
  public qrCode: any = 'aaaa';
  public pedido: any = null;

  constructor(private navParams: NavParamsService, private genericService: GenericService) {
    this.pedido = navParams.get('pedido');
    console.log(this.pedido);

    this.qrCode = this.pedido.token;
  }

  ngOnInit() {
    console.log(this.qrCode);
    new QRCode(document.getElementById('qrcode'), this.qrCode);
  }
}
