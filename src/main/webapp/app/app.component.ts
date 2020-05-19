import { AlertService } from './services/alert.service';
import { JhiEventManager } from 'ng-jhipster';
import { Component, OnDestroy, OnInit, ElementRef, AfterViewInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { LoginModalService } from './core';
import swal, { SweetAlertOptions } from 'sweetalert2';
import { LoadingService } from './services/loading-service';
import { MessagingService } from './services/firebase.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnDestroy, OnInit, AfterViewInit {
  title = 'basic-with-login';
  modalRef: NgbModalRef;
  public eventoLogin: any = null;
  constructor(
    private eventManager: JhiEventManager,
    private loginModalService: LoginModalService,
    private alertService: AlertService,
    private loadingService: LoadingService,
    private messagingService: MessagingService,
    private elementRef: ElementRef
  ) {
    console.log('app ejecutandose');
    this.eventoLogin = this.eventManager.subscribe('startSession', response => {
      console.log('logica login');
      this.loadingService.hide();
      swal
        .fire({
          title: 'Para continuar es necesario que inicies sesión',
          text: '¿Deseas iniciar sesión?',
          type: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#d33',
          confirmButtonText: 'Aceptar'
        })
        .then(result => {
          console.log(result);

          if (result.value) {
            this.modalRef = this.loginModalService.open();
          }
        });
    });
  }

  ngAfterViewInit() {
    this.iniSlickJs();
  }

  private iniSlickJs() {
    const htmlScriptElement = document.createElement('script');
    htmlScriptElement.src = 'https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.9.0/slick.min.js';
    this.elementRef.nativeElement.appendChild(htmlScriptElement);

    const htmlScriptElement2 = document.createElement('link');
    htmlScriptElement2.href = 'https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.9.0/slick-theme.css';
    htmlScriptElement2.rel = 'stylesheet';
    this.elementRef.nativeElement.appendChild(htmlScriptElement2);

    const htmlScriptElement3 = document.createElement('link');
    htmlScriptElement3.href = 'https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.9.0/slick.css';
    htmlScriptElement3.rel = 'stylesheet';
    this.elementRef.nativeElement.appendChild(htmlScriptElement3);
  }

  ngOnInit() {
    //this.messagingService.requestPermission();
    //this.messagingService.receiveMessage();
    //console.log("*****************************************");
    //console.log(this.messagingService.currentMessage);
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventoLogin);
  }
}
