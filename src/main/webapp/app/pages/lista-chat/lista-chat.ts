import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, ActionSheet } from 'ionic-angular';
import { GenericService } from '../../services/generic.service';
import { TranslateService } from '@ngx-translate/core';
import { environment } from '../../../environments/environment.prod';
import { HttpErrorResponse } from '@angular/common/http';
import { AlertService } from 'app/services/alert.service';
import { LoadingService } from 'app/services/loading-service';

@Component({
  selector: 'page-lista-chat',
  templateUrl: 'lista-chat.html',
  styleUrls: ['./lista-chat.scss']
})
export class ListaChatPage {
  public chats: any = null;
  public pedido: any = null;

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    private genericService: GenericService,
    private alertaService: AlertService,
    private loadingService: LoadingService,
    private translatePipe: TranslateService
  ) {
    this.chats = navParams.get('chats');
    this.pedido = navParams.get('pedido');

    console.log(this.chats);
    console.log(this.pedido);
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad ListaChatPage');
  }

  verChat(chat: any) {
    this.loadingService.show();
    this.genericService.sendGetRequest(`${environment.chats}/${chat.chatProveedorid}`).subscribe(
      (response: any) => {
        this.loadingService.hide();
        this.navCtrl.push('chat', { chat: response, pedido: this.pedido });
      },
      (error: HttpErrorResponse) => {
        this.loadingService.hide();
        let err: any = error.error;
        this.alertaService.errorAlertGeneric(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
      }
    );
  }
}
