import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ValidationService } from '../../services/validation.service';
import { TranslateService } from '@ngx-translate/core';
import { HomeGeoProveedoresPage } from '../home-geo-proveedores/home-geo-proveedores';
import { LocalStorageEncryptService } from 'app/services/local-storage-encrypt-service';
import { NavParamsService } from 'app/services/nav-params.service';
import { AlertService } from 'app/services/alert.service';
import { LoadingService } from 'app/services/loading-service';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../../environments/environment.prod';
import * as moment from 'moment';
import { GenericService } from 'app/services/generic.service';
import { ActivatedRoute, RouterStateSnapshot, Router } from '@angular/router';

@Component({
  selector: 'page-registro',
  templateUrl: 'registro.html',
  styleUrls: ['./registro.scss']
})
export class RegistroPage {
  public photo_url: string = null;

  public onlyPhoto: boolean = false;

  public selectOptions: any = {
    cssClass: 'action-sheet-class'
  };

  public tipoUsuario: string = '2';

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
          value: '[--Género--]'
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
    },
    {
      name: 'Correo electrónico',
      required: true,
      length: 100,
      type: 'email',
      formName: 'email',
      value: null
    },
    {
      name: 'Contraseña',
      required: true,
      length: 50,
      type: 'password',
      formName: 'pass',
      value: null
    },
    {
      name: 'Confirmar contraseña',
      required: true,
      length: 50,
      type: 'password',
      formName: 'passC',
      value: null
    }
  ];

  public formGroup: FormGroup = null;

  public btnHabilitado: boolean = true;

  public user: any = null;

  public data: any = null;
  constructor(
    private route: ActivatedRoute,
    public formBuilder: FormBuilder,
    private localStorageEncryptService: LocalStorageEncryptService,
    private translatePipe: TranslateService,
    private alertaService: AlertService,
    private genericService: GenericService,
    private loadingService: LoadingService,
    private modalController: NgbModal,
    private router: Router
  ) {
    this.user = this.localStorageEncryptService.getFromLocalStorage('userSession');
    let putObj: any = {};

    this.route.queryParams.subscribe(params => {
      this.tipoUsuario = params['tipoUsuario'];
      this.formGroup = null;
      putObj = {};
      this.photo_url = null;
      this.onlyPhoto = false;
      this.resetObjetoRegistro();
      switch (this.tipoUsuario) {
        case '3':
          this.objetoRegistro.splice(7, 0, {
            name: 'Tipo persona',
            required: true,
            length: 11,
            type: 'select',
            formName: 'typpe',
            value: 0,
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

          this.objetoRegistro.splice(8, 0, {
            name: 'Razón Social',
            required: true,
            length: 50,
            type: 'text',
            formName: 'rz',
            value: null
          });

          this.objetoRegistro.push({
            name: 'Dirección',
            required: true,
            length: 200,
            type: 'text',
            formName: 'direc',
            value: null,
            disabled: true
          });
          break;

        case '4':
          this.objetoRegistro.splice(7, 0, {
            name: 'Tipo persona',
            required: true,
            length: 11,
            type: 'select',
            formName: 'typpe',
            value: 0,
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

          this.objetoRegistro.splice(8, 0, {
            name: 'Razón Social',
            required: true,
            length: 50,
            type: 'text',
            formName: 'rz',
            value: null
          });
          break;
      }

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

        if (this.user) {
        }

        putObj[item.formName] = tmp;
      });

      this.formGroup = this.formBuilder.group(putObj);
    });
  }

  resetObjetoRegistro() {
    this.objetoRegistro = [
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
            value: '[--Género--]'
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
      },
      {
        name: 'Correo electrónico',
        required: true,
        length: 100,
        type: 'email',
        formName: 'email',
        value: null
      },
      {
        name: 'Contraseña',
        required: true,
        length: 50,
        type: 'password',
        formName: 'pass',
        value: null
      },
      {
        name: 'Confirmar contraseña',
        required: true,
        length: 50,
        type: 'password',
        formName: 'passC',
        value: null
      }
    ];
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

  registrar() {
    if (this.tipoUsuario == '2' && this.objetoRegistro[7].value != this.objetoRegistro[8].value) {
      this.alertaService.warn('Las contraseñas no coinciden');
    } else if ((this.tipoUsuario == '3' || this.tipoUsuario == '4') && this.objetoRegistro[9].value != this.objetoRegistro[10].value) {
      this.alertaService.warn('Las contraseñas no coinciden');
    } else {
      let body: any = null;

      switch (this.tipoUsuario) {
        case '2':
          body = {
            login: this.objetoRegistro[6].value,
            email: this.objetoRegistro[6].value,
            firstName: this.objetoRegistro[0].value,
            lastName: this.objetoRegistro[1].value,
            motherLastName: this.objetoRegistro[2].value,

            telefono: this.objetoRegistro[4].value,
            fechaNacimiento: moment(this.objetoRegistro[3].value, 'YYYY-MM-DD').format('DD/MM/YYYY'),

            genero: this.objetoRegistro[5].value,
            password: this.objetoRegistro[7].value,

            activated: true, // por default en la app

            adjunto:
              this.photo_url == null || this.photo_url == 'null'
                ? null
                : {
                    contentType: 'image/jpeg',
                    file: this.photo_url.split('data:image/jpeg;base64,')[1],
                    fileName: Math.floor(new Date().getTime() / 1000.0).toString(),
                    size: 0
                  }
          };
          break;

        case '3':
          body = {
            login: this.objetoRegistro[6].value,
            email: this.objetoRegistro[6].value,
            firstName: this.objetoRegistro[0].value,
            lastName: this.objetoRegistro[1].value,
            motherLastName: this.objetoRegistro[2].value,

            telefono: this.objetoRegistro[4].value,
            fechaNacimiento: moment(this.objetoRegistro[3].value, 'YYYY-MM-DD').format('DD/MM/YYYY'),

            genero: this.objetoRegistro[5].value,
            password: this.objetoRegistro[9].value,

            tipoPersona: this.objetoRegistro[7].value,
            razonSocial: this.objetoRegistro[8].value,

            activated: true, // por default en la app

            adjunto:
              this.photo_url == null || this.photo_url == 'null'
                ? null
                : {
                    contentType: 'image/jpeg',
                    file: this.photo_url,
                    fileName: Math.floor(new Date().getTime() / 1000.0).toString(),
                    size: 0
                  },

            direccion: {
              codigoPostal: this.data.codigoPostal,
              direccion: this.data.direccion,
              latitud: this.data.latitud,
              longitud: this.data.longitud
            }
          };
          break;
        case '4':
          body = {
            login: this.objetoRegistro[6].value,
            email: this.objetoRegistro[6].value,
            firstName: this.objetoRegistro[0].value,
            lastName: this.objetoRegistro[1].value,
            motherLastName: this.objetoRegistro[2].value,

            telefono: this.objetoRegistro[4].value,
            fechaNacimiento: moment(this.objetoRegistro[3].value, 'YYYY-MM-DD').format('DD/MM/YYYY'),

            genero: this.objetoRegistro[5].value,
            password: this.objetoRegistro[9].value,

            tipoPersona: this.objetoRegistro[7].value,
            razonSocial: this.objetoRegistro[8].value,

            activated: true, // por default en la app

            adjunto:
              this.photo_url == null || this.photo_url == 'null'
                ? null
                : {
                    contentType: 'image/jpeg',
                    file: this.photo_url,
                    fileName: Math.floor(new Date().getTime() / 1000.0).toString(),
                    size: 0
                  }
          };
          break;
      }

      let path: string = environment.registro;

      if (this.tipoUsuario == '3') {
        body.tipoPersona = 2;
        path = `${environment.registro}/proveedor`;
      }
      if (this.tipoUsuario == '4') {
        body.tipoPersona = 3;
        path = `${environment.registro}/transportista`;
      }

      if (this.tipoUsuario == '3' && this.objetoRegistro[this.objetoRegistro.length - 1].value.length <= 0) {
        this.alertaService.warn('Es necesario que ingreses tu dirección');
      } else {
        this.loadingService.show();

        this.genericService.sendPostRequest(path, body).subscribe(
          (response: any) => {
            this.loadingService.hide();
            this.alertaService.success('Registro exitoso');
          },
          (error: HttpErrorResponse) => {
            this.loadingService.hide();
            let err: any = error.error;
            this.alertaService.error(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
          }
        );
        this.loadingService.hide();
      }
    }
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
