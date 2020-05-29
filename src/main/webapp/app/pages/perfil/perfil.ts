import { JhiEventManager } from 'ng-jhipster';

import { GenericService } from './../../services/generic.service';
import { Component, OnInit } from '@angular/core';
import { IonicPage, NavController, NavParams, Events, ModalController, ActionSheetController } from 'ionic-angular';
import { environment } from '../../../environments/environment.prod';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { ValidationService } from '../../services/validation.service';
import * as moment from 'moment';
import { HomeGeoProveedoresPage } from '../home-geo-proveedores/home-geo-proveedores';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
import { NavParamsService } from 'app/services/nav-params.service';
import { AlertService } from 'app/services/alert.service';
import { LoadingService } from 'app/services/loading-service';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'page-perfil',
  templateUrl: 'perfil.html',
  styleUrls: ['./perfil.scss']
})
export class PerfilPage implements OnInit {
  public photo_url: string = null;

  public selectOptions: any = {
    cssClass: 'action-sheet-class'
  };

  public onlyPhoto: boolean = false;

  //this.objetoRegistro[0].value =
  public objetoRegistro: any[] = [
    {
      name: 'Nombre',
      required: true,
      length: 50,
      type: 'text',
      formName: 'name',
      value: null
    },
    {
      name: 'Apellido paterno',
      required: true,
      length: 50,
      type: 'text',
      formName: 'ap',
      value: null
    },
    {
      name: 'Apellido materno',
      required: true,
      length: 50,
      type: 'text',
      formName: 'am',
      value: null
    },
    {
      name: 'Fecha de nacimiento',
      required: true,
      length: 10,
      type: 'date',
      formName: 'fecha',
      value: null
    },
    {
      name: 'Teléfono',
      required: true,
      length: 10,
      type: 'number',
      formName: 'tel',
      value: null
    },
    {
      name: 'Género',
      required: true,
      length: 11,
      type: 'select',
      formName: 'sex',
      value: 0,
      opts: [
        {
          id: 0,
          value: '[--Selecciona--]'
        },
        {
          id: 'M',
          value: 'Hombre'
        },
        {
          id: 'F',
          value: 'Mujer'
        }
      ]
    }
  ];

  public formGroup: FormGroup = null;

  public btnHabilitado: boolean = true;

  public user: any = null;

  public userResponse: any = null;

  public env: any = environment;

  public data: any = null;
  constructor(
    public navCtrl: NavParamsService,
    public formBuilder: FormBuilder,
    private localStorageEncryptService: LocalStorageEncryptService,
    private translatePipe: TranslateService,
    private alertaService: AlertService,
    private genericService: GenericService,
    private loadingService: LoadingService,
    private events: JhiEventManager,
    private modalController: NgbModal
  ) {
    this.user = this.localStorageEncryptService.getFromLocalStorage('userSession');

    this.events.subscribe('reloadUser', data => {
      try {
        this.user = this.localStorageEncryptService.getFromLocalStorage('userSession');
      } catch (error) {}
    });
  }

  ngOnInit() {
    moment.locale('ES');
    this.getDataUsuario();
  }

  getDataUsuario() {
    this.loadingService.show();
    this.genericService.sendGetRequest(`${environment.users}/${this.user.username}`).subscribe(
      (response: any) => {
        this.objetoRegistro[0].value = response.firstName;
        this.objetoRegistro[1].value = response.lastName;
        this.objetoRegistro[2].value = response.motherLastName;
        if (response.fechaNacimiento) {
          this.objetoRegistro[3].value = moment(response.fechaNacimiento, 'DD/MM/YYYY')
            .format('YYYY/MM/DD')
            .toString();
          console.log(this.objetoRegistro[3].value);
          let id: any = document.getElementById(this.objetoRegistro[3].formName);
          console.log(id);
        } else {
          this.objetoRegistro[3].value = null;
        }
        this.objetoRegistro[4].value = response.telefono ? response.telefono : null;
        this.objetoRegistro[5].value = response.genero ? response.genero : null;
        let user: any = this.localStorageEncryptService.getFromLocalStorage('userSession');
        //if (user.tipo_usuario == 4) {
        if (user.tipo_usuario == 3) {
          this.objetoRegistro.push({
            name: 'Tipo persona',
            required: true,
            length: 11,
            type: 'select',
            formName: 'typpe',
            value: response.tipoPersonaId,
            opts: [
              {
                id: 0,
                value: '[--Tipo persona--]'
              },
              {
                id: 1,
                value: 'Persona física'
              },
              {
                id: 2,
                value: 'Persona moral'
              }
            ]
          });

          this.objetoRegistro.push({
            name: 'Razón Social',
            required: true,
            length: 50,
            type: 'text',
            formName: 'rz',
            value: response.razonSocial
          });

          this.objetoRegistro.push({
            name: 'Dirección',
            required: true,
            length: 200,
            type: 'text',
            formName: 'direc',
            value: response.direccion ? response.direccion.direccion : null,
            disabled: true
          });

          this.data = response.direccion ? response.direccion : null;
        } else if (user.tipo_usuario == 4) {
          this.objetoRegistro.push({
            name: 'Tipo persona',
            required: true,
            length: 11,
            type: 'select',
            formName: 'typpe',
            value: response.tipoPersonaId,
            opts: [
              {
                id: 0,
                value: '[--Tipo persona--]'
              },
              {
                id: 1,
                value: 'Persona física'
              },
              {
                id: 2,
                value: 'Persona moral'
              }
            ]
          });

          this.objetoRegistro.push({
            name: 'Razón Social',
            required: true,
            length: 50,
            type: 'text',
            formName: 'rz',
            value: response.razonSocial
          });
        }

        this.userResponse = response;
        console.log(this.userResponse);

        let putObj: any = {};
        this.objetoRegistro.forEach(item => {
          let tmp: any[] = [];
          tmp[0] = null;
          tmp[1] = [];
          if (item.required) {
            tmp[1].push(Validators.required);
          }

          if (item.type == 'number') {
            tmp[1].push(ValidationService.phoneValidator);
            tmp[1].push(ValidationService.maxLengthValidator);
            tmp[1].push(ValidationService.minLengthValidator);
          }

          if (item.type == 'email') {
            tmp[1].push(ValidationService.emailValidator);
          }

          if (item.type == 'password') {
            tmp[1].push(ValidationService.passwordValidator);
          }

          if (item.type == 'select') {
            tmp[0] = item.opts[0].value;
          }

          putObj[item.formName] = tmp;
        });

        this.formGroup = this.formBuilder.group(putObj);

        setTimeout(() => {
          this.objetoRegistro[3].value = moment(response.fechaNacimiento, 'DD/MM/YYYY')
            .format('YYYY-MM-DD')
            .toString();
          console.log(this.objetoRegistro[3]);
          let id: any = document.getElementById(this.objetoRegistro[3].formName);
          console.log(id);
          id.value = this.objetoRegistro[3].value;
        }, 500);

        this.btnHabilitado = false;

        this.loadingService.hide();
      },
      (error: HttpErrorResponse) => {
        this.loadingService.hide();
        let err: any = error.error;
        this.alertaService.errorAlertGeneric(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
      }
    );
  }

  cambiarPerfil() {
    this.loadingService.show();

    /**
     this.objetoRegistro[0].value = response.firstName;
      this.objetoRegistro[1].value = response.lastName;
      this.objetoRegistro[2].value = response.motherLastName;
      this.objetoRegistro[3].value = moment(response.fechaNacimiento, "DD/MM/YYYY").toDate().toISOString();
      this.objetoRegistro[4].value = response.telefono;
      this.objetoRegistro[5].value = response.genero;
     */
    console.log(this.objetoRegistro);
    //console.log(this.photo_url);
    let formateado: any = null;

    if (this.photo_url.split('data:image/png;base64,')[1]) {
      formateado = this.photo_url.split('data:image/png;base64,')[1];
      this.upload(formateado);
    } else if (this.photo_url.split('data:image/jpg;base64,')[1]) {
      formateado = this.photo_url.split('data:image/jpg;base64,')[1];
      this.upload(formateado);
    } else if (this.photo_url.split('data:image/jpeg;base64,')[1]) {
      formateado = this.photo_url.split('data:image/jpeg;base64,')[1];
      this.upload(formateado);
    } else {
      this.loadingService.hide();
      this.alertaService.warn('Sube una imagen con formato correcto, PNG o JPG');
    }
  }

  upload(formateado) {
    let body: any = {
      login: this.user.username,
      firstName: this.objetoRegistro[0].value,
      lastName: this.objetoRegistro[1].value,
      motherLastName: this.objetoRegistro[2].value,
      telefono: this.objetoRegistro[4].value,
      genero: this.objetoRegistro[5].value,
      fechaNacimiento: moment(this.objetoRegistro[3].value, 'YYYY-MM-DD').format('DD/MM/YYYY'),
      adjunto:
        this.photo_url == null || this.photo_url == 'null'
          ? null
          : {
              contentType: 'image/jpeg',
              file: formateado,
              fileName: Math.floor(new Date().getTime() / 1000.0).toString(),
              size: 0
            }
    };
    let user: any = this.localStorageEncryptService.getFromLocalStorage('userSession');
    //if (user.tipo_usuario == 4) {
    if (user.tipo_usuario == 3) {
      body.direccion = {
        codigoPostal: this.data.codigoPostal,
        direccion: this.data.direccion,
        latitud: this.data.latitud,
        longitud: this.data.longitud
      };

      body.tipoPersonaId = this.objetoRegistro[6].value;
      body.razonSocial = this.objetoRegistro[7].value;
    } else if (user.tipo_usuario == 4) {
      body.tipoPersonaId = this.objetoRegistro[6].value;
      body.razonSocial = this.objetoRegistro[7].value;
    }

    this.genericService.sendPutRequest(environment.usuarios, body).subscribe(
      (response: any) => {
        this.alertaService.info('Perfil modificado con éxito');
        this.loadingService.hide();
      },
      (error: HttpErrorResponse) => {
        this.loadingService.hide();
        this.alertaService.errorAlertGeneric('No se ha podido modificar tu perfil, intenta nuevamente');
      }
    );
  }

  getBase64(file, componente) {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function() {
      componente.photo_url = reader.result;
    };
    reader.onerror = function(error) {};
  }

  changeFile(evt: any) {
    let files = evt.target.files;
    let file = files[0];
    console.log(file);
    this.getBase64(file, this);
    console.log(this.photo_url);
    this.onlyPhoto = true;
  }

  /**Verifica validaciones */
  ejecutaValidator() {
    let validacion: number = 0;
    for (const name in this.formGroup.controls) {
      let n: any = this.formGroup.controls[name];

      if (n.value === 0) {
        validacion++;
      }
      if (n.errors) {
        validacion++;
      }
      /*
      if (n.value && (n.value === 0 || n.value.length === 0) && n.invalid) {
        invalid.push(this.translatePipe.instant(String(name).toUpperCase()));
        fields += `${this.translatePipe.instant(String(name).toUpperCase())}, `;
      } */
    }
    if (validacion <= 0) {
      this.btnHabilitado = false;
    } else {
      this.btnHabilitado = true;
    }
  }

  opcionesDeImagen() {}

  getMapa() {
    let ngbModalOptions: NgbModalOptions = {
      backdrop: 'static',
      keyboard: false,
      backdropClass: 'backdrop1'
    };
    let modal: any = this.modalController.open(HomeGeoProveedoresPage, ngbModalOptions);

    modal.componentInstance.fromModal = true;
    modal.componentInstance.fromRegister = true;

    modal.result.then(
      result => {
        let data = result;

        if (data) {
          if (data != null) {
            this.data = data.data;
            this.objetoRegistro[this.objetoRegistro.length - 1].value = this.data.direccion;
            setTimeout(() => {
              this.ejecutaValidator();
            }, 500);
          }
        }
      },
      reason => {}
    );
  }
}
