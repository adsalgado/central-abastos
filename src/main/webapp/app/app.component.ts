import { AlertService } from './services/alert.service';
import { JhiEventManager } from 'ng-jhipster';
import { Component, OnDestroy } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { LoginModalService } from './core';
import swal, { SweetAlertOptions } from 'sweetalert2';
import { LoadingService } from './services/loading-service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnDestroy {
  title = 'basic-with-login';
  modalRef: NgbModalRef;
  public eventoLogin: any = null;
  constructor(
    private eventManager: JhiEventManager,
    private loginModalService: LoginModalService,
    private alertService: AlertService,
    private loadingService: LoadingService
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

  ngOnDestroy() {
    this.eventManager.destroy(this.eventoLogin);
  }
}
