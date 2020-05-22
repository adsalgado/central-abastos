import { GenericService } from './../../services/generic.service';
import { Component, ViewChild, OnDestroy } from '@angular/core';
import { IonicPage, NavController, NavParams, Events, Content } from 'ionic-angular';
import { HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../../environments/environment.prod';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
import { AlertService } from 'app/services/alert.service';

@Component({
  selector: 'page-chat',
  templateUrl: 'chat.html',
  styleUrls: ['./chat.scss']
})
export class ChatPage implements OnDestroy {
  public chat: any = null;

  public pedido: any = null;

  public user: any = null;

  public color: any = '#3b64c0';

  public mensaje: string = '';

  public toggled: boolean = false;
  public emojitext: string;

  public env: any = environment;

  public intervalo: any = null;

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    private localStorageEncryptService: LocalStorageEncryptService,
    private events: Events,
    private genericService: GenericService,
    private alertaService: AlertService
  ) {
    this.chat = navParams.get('chat');
    this.pedido = navParams.get('pedido');

    this.user = this.localStorageEncryptService.getFromLocalStorage('userSession');

    if (this.localStorageEncryptService.getFromLocalStorage('theme')) {
      this.color = this.localStorageEncryptService.getFromLocalStorage('theme');
    }
    this.events.subscribe('changeColor', data => {
      try {
        if (this.localStorageEncryptService.getFromLocalStorage('theme')) {
          this.color = this.localStorageEncryptService.getFromLocalStorage('theme');
        }
      } catch (error) {}
    });

    this.events.subscribe('updateChat', data => {
      try {
        this.verChat();
      } catch (error) {}
    });
  }

  ngOnDestroy() {
    this.events.unsubscribe('updateChat');
    let claseTabs: any = document.getElementsByClassName('tabbar');
    claseTabs[0].style.display = 'flex';
    clearInterval(this.intervalo);
    this.intervalo = null;
  }

  handleSelection(event) {
    this.mensaje = this.mensaje + ' ' + event.char;
  }

  ionViewDidLoad() {
    let claseTabs: any = document.getElementsByClassName('tabbar');
    claseTabs[0].style.display = 'none';
    window.scrollTo(0, document.body.scrollHeight);
    this.intervalo = setInterval(() => {
      this.verChat();
    }, 2000);
  }

  verChat() {
    switch (environment.perfil.activo) {
      case 1:
        this.genericService.sendGetRequest(`${environment.chats}/${this.chat.id}`).subscribe(
          (response: any) => {
            if (this.chat.chatDetalles.length < response.chatDetalles) {
              window.scrollTo(0, document.body.scrollHeight);
            }
            this.chat = response;
          },
          (error: HttpErrorResponse) => {
            let err: any = error.error;
            //this.alertaService.errorAlertGeneric(err.message ? err.message : "Ocurrió un error en el servicio, intenta nuevamente");
          }
        );
        break;

      case 2:
        this.genericService.sendGetRequest(`${environment.chatsProveedor}${this.pedido.pedidoProveedores[0].id}/tipoChat/1`).subscribe(
          (response: any) => {
            if (this.chat.chatDetalles.length < response.chatDetalles) {
              window.scrollTo(0, document.body.scrollHeight);
            }
            this.chat = response;
          },
          (error: HttpErrorResponse) => {
            let err: any = error.error;
            //this.alertaService.errorAlertGeneric(err.message ? err.message : "Ocurrió un error en el servicio, intenta nuevamente");
          }
        );
        break;
      case 3:
        this.genericService.sendGetRequest(`${environment.chatsProveedor}${this.pedido.pedidoProveedores[0].id}/tipoChat/2`).subscribe(
          (response: any) => {
            if (this.chat.chatDetalles.length < response.chatDetalles) {
              window.scrollTo(0, document.body.scrollHeight);
            }
            this.chat = response;
          },
          (error: HttpErrorResponse) => {
            let err: any = error.error;
            //this.alertaService.errorAlertGeneric(err.message ? err.message : "Ocurrió un error en el servicio, intenta nuevamente");
          }
        );
        break;
    }
  }

  sendMessage() {
    if (this.mensaje.length === 0) {
      return;
    }

    //this.chatService.sendMessage(this.mensaje);
    let body: any = {
      chatId: this.chat.id,
      from: this.user.username,

      text: this.mensaje.toString()
    };

    if (environment.perfil.activo == 1) {
      body.to = `${this.chat.chatDetalles[0].usuarioEmisorLogin}`;
    } else if (environment.perfil.activo == 2) {
      body.to = `${this.pedido.cliente.login}`;
    }

    this.genericService.sendPostRequest(`${environment.chats}/messages`, body).subscribe(
      (response: any) => {
        this.chat.chatDetalles.push(response);
        window.scrollTo(0, document.body.scrollHeight);
        this.mensaje = '';
      },
      (error: HttpErrorResponse) => {
        this.alertaService.errorAlertGeneric('No se ha podido enviar tu mensaje, intenta nuevamente');
      }
    );
  }
}
