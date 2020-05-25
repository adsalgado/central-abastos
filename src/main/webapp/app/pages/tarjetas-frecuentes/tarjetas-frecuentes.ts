import { JhiEventManager } from 'ng-jhipster';

import { GenericService } from './../../services/generic.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { environment } from '../../../environments/environment.prod';
import { HttpErrorResponse } from '@angular/common/http';
import { NavParamsService } from 'app/services/nav-params.service';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
import { AlertService } from 'app/services/alert.service';
import { LoadingService } from 'app/services/loading-service';

@Component({
  selector: 'page-tarjetas-frecuentes',
  templateUrl: 'tarjetas-frecuentes.html',
  styleUrls: ['./tarjetas-frecuentes.scss']
})
export class TarjetasFrecuentesPage implements OnDestroy, OnInit {
  public cards: any = null;
  public user: any = null;

  public dataEvents: any = {};
  constructor(
    public navCtrl: NavParamsService,
    private localStorageEncryptService: LocalStorageEncryptService,
    private events: JhiEventManager,
    private genericService: GenericService,
    private alertaService: AlertService,
    private loadingService: LoadingService
  ) {
    this.user = this.localStorageEncryptService.getFromLocalStorage(`userSession`);

    this.dataEvents.card = this.events.subscribe('card', data => {
      if (!data.create) {
        let position = this.cards.findIndex(img => {
          return img.id == data.response.id;
        });
        for (let index = 0; index < this.cards.length; index++) {
          const element = this.cards[index];
          if (element.id == data.response.id) {
            position = index;
          }
        }
        this.cards[position] = data.response;
      } else {
        this.cards.push(data.response);
      }
      //this.cards = this.localStorageEncryptService.getFromLocalStorage(`${this.user.id_token}-cards`);
    });

    this.dataEvents.actualiza = this.events.subscribe('actualizarTarjetas', data => {
      this.getCards();
    });
  }

  getCards() {
    this.genericService.sendGetRequest(environment.tarjetas).subscribe(
      (response: any) => {
        //quitar
        this.cards = response;
        if (this.cards.length <= 0) {
          this.alertaService.warn('Aún no cuentas con tarjetas frecuentes');
          //this.navCtrl.push("main/public-home");
        }
      },
      (error: HttpErrorResponse) => {
        let err: any = error.error;
        this.cards = null;
        //this.alertaService.errorAlertGeneric(err.message ? err.message : "Ocurrió un error en el servicio, intenta nuevamente");
      }
    );
  }

  ngOnDestroy() {
    this.events.destroy(this.dataEvents.card);
    this.events.destroy(this.dataEvents.actualiza);
  }

  ngOnInit() {
    this.getCards();
  }

  addCard(card: any = null) {
    let position: any;
    if (this.cards && card) {
      position = this.cards.findIndex(img => {
        return img.id == card.id;
      });
    } else {
      position = -1;
    }
    this.navCtrl.push('main/detalle-tarjeta', { card, position, cards: this.cards, edit: card ? true : false });
  }

  borrar(item: any) {
    let position: any = this.cards.findIndex(img => {
      return img.id == item.id;
    });

    this.loadingService.show();
    this.genericService.sendDelete(`${environment.tarjetas}/${item.id}`).subscribe(
      (response: any) => {
        this.cards = [...this.cards.slice(0, position), ...this.cards.slice(position + 1)];
        this.loadingService.hide();
      },
      (error: HttpErrorResponse) => {
        this.loadingService.hide();
        this.alertaService.errorAlertGeneric('No se ha podido eliminar tu tarjeta, intenta nuevamente');
      }
    );
  }
}
