import { JhiEventManager } from 'ng-jhipster';
import { GenericService } from './../../services/generic.service';
import { Component, ViewChild, OnDestroy, OnInit } from '@angular/core';
import { IonicPage, NavController, NavParams, Events, Content } from 'ionic-angular';
import { HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../../environments/environment.prod';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
import { AlertService } from 'app/services/alert.service';
import { NavParamsService } from 'app/services/nav-params.service';

@Component({
  selector: 'page-chat',
  templateUrl: 'chat.html',
  styleUrls: ['./chat.scss']
})
export class ChatPage implements OnDestroy, OnInit {
  public chat: any = null;

  public pedido: any = null;

  public user: any = null;

  public color: any = '#3b64c0';

  public mensaje: string = '';

  public toggled: boolean = false;
  public emojitext: string;

  public env: any = environment;

  public intervalo: any = null;

  public dataEvents: any = {};

  public subscription: any = null;

  constructor(
    private navParams: NavParamsService,
    private localStorageEncryptService: LocalStorageEncryptService,
    private events: JhiEventManager,
    private genericService: GenericService,
    private alertaService: AlertService
  ) {
    this.chat = navParams.get('chat');

    this.user = this.localStorageEncryptService.getFromLocalStorage('userSession');
    if (this.user.tipo_usuario <= 1) {
      this.pedido = navParams.get('pedidoc');
    } else {
      this.pedido = navParams.get('pedido');
    }

    if (this.localStorageEncryptService.getFromLocalStorage('theme')) {
      this.color = this.localStorageEncryptService.getFromLocalStorage('theme');
    }
    this.dataEvents.uno = this.events.subscribe('changeColor', data => {
      try {
        if (this.localStorageEncryptService.getFromLocalStorage('theme')) {
          this.color = this.localStorageEncryptService.getFromLocalStorage('theme');
        }
      } catch (error) {}
    });

    this.dataEvents.dos = this.events.subscribe('updateChat', data => {
      try {
        this.verChat();
      } catch (error) {}
    });
  }

  ngOnDestroy() {
    this.events.destroy(this.dataEvents.dos);
    this.events.destroy(this.dataEvents.uno);

    clearInterval(this.intervalo);
    this.intervalo = null;
  }

  handleSelection(event) {
    this.mensaje = this.mensaje + ' ' + event.char;
  }

  ngOnInit() {
    window.scrollTo(0, document.body.scrollHeight);
    this.intervalo = setInterval(() => {
      this.verChat();
    }, 2000);
  }

  verChat() {
    let user: any = this.localStorageEncryptService.getFromLocalStorage('userSession');
    //if (user.tipo_usuario == 4) {
    switch (user.tipo_usuario) {
      case 2:
        if (this.subscription) {
          this.subscription.unsubscribe();
        }
        this.subscription = this.genericService.sendGetRequest(`${environment.chats}/${this.chat.id}`).subscribe(
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
        if (this.subscription) {
          this.subscription.unsubscribe();
        }
        this.subscription = this.genericService
          .sendGetRequest(`${environment.chatsProveedor}${this.pedido.pedidoProveedores[0].id}/tipoChat/1`)
          .subscribe(
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
      case 4:
        if (this.subscription) {
          this.subscription.unsubscribe();
        }
        this.subscription = this.genericService
          .sendGetRequest(`${environment.chatsProveedor}${this.pedido.pedidoProveedores[0].id}/tipoChat/2`)
          .subscribe(
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
    let user: any = this.localStorageEncryptService.getFromLocalStorage('userSession');
    //if (user.tipo_usuario == 4) {
    if (user.tipo_usuario == 2) {
      body.to = `${this.chat.chatDetalles[0].usuarioEmisorLogin}`;
    } else if (user.tipo_usuario == 3) {
      body.to = `${this.pedido.cliente.login}`;
    }

    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    this.subscription = this.genericService.sendPostRequest(`${environment.chats}/messages`, body).subscribe(
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
