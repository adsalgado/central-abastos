import { Component } from '@angular/core';
import { GenericService } from '../../services/generic.service';
import { environment } from '../../../environments/environment.prod';
import { HttpErrorResponse } from '@angular/common/http';
import { TranslateService } from '@ngx-translate/core';
import { AlertService } from 'app/services/alert.service';

@Component({
  selector: 'page-documentos',
  templateUrl: 'documentos.html'
})
export class DocumentosPage {
  public photo_url: string = null;

  public documentos: any[] = [];

  public documentosTmp: any[] = [];

  constructor(private genericService: GenericService, private alertaService: AlertService, private translatePipe: TranslateService) {
    this.documentosTmp.push({
      documentoId: 1,
      nombre: 'IFE',
      adjuntoId: null,
      usuarioDocumentoId: null,
      imagen: null
    });
    this.documentosTmp.push({
      documentoId: 2,
      nombre: 'Comprobante de domicilio',
      adjuntoId: null,
      usuarioDocumentoId: null,
      imagen: null
    });
    this.documentosTmp.push({
      documentoId: 3,
      nombre: 'Estado de cuenta',
      adjuntoId: null,
      usuarioDocumentoId: null,
      imagen: null
    });
    this.documentosTmp.push({
      documentoId: 4,
      nombre: 'Foto de la fachada',
      adjuntoId: null,
      usuarioDocumentoId: null,
      imagen: null
    });

    this.cargarDocs();
  }

  cargarDocs() {
    this.genericService.sendGetRequest(`${environment.usuarioDocumentos}`).subscribe(
      (response: any) => {
        console.log(response);
        this.documentos = response;
        if (this.documentos.length <= 0) {
          //this.documentos = null;
          this.documentos = this.documentosTmp;
          console.log(this.documentos);
        } else {
          console.log('no fue necesario');
          this.documentos.forEach(element => {
            if (element.adjuntoId) {
              element.imagen = `${environment.getImagenIndividual}${element.adjuntoId}`;
            }
          });
          console.log(this.documentos);
        }
      },
      (error: HttpErrorResponse) => {
        let err: any = error.error;
        this.alertaService.errorAlertGeneric(err.message ? err.message : 'Ocurrió un error en el servicio, intenta nuevamente');
      }
    );
  }

  actualizar(documento: any) {
    let formateado: any = null;

    if (documento.image.split('data:image/png;base64,')[1]) {
      formateado = documento.image.split('data:image/png;base64,')[1];
    } else if (documento.image.split('data:image/jpg;base64,')[1]) {
      formateado = documento.image.split('data:image/jpg;base64,')[1];
    } else if (documento.image.split('data:image/jpeg;base64,')[1]) {
      formateado = documento.image.split('data:image/jpeg;base64,')[1];
    }

    let body: any = {
      documentoId: documento.documentoId,
      adjunto: {
        fileName: `${Math.round(new Date().getTime() / 1000)}.jpg`,
        contentType: 'jpg',
        size: null,
        file: formateado
      }
    };

    this.genericService.sendPostRequest(`${environment.usuarioDocumentos}`, body).subscribe(
      (response: any) => {
        this.alertaService.success('Tu documento se actualizó correctamente');
      },
      (error: HttpErrorResponse) => {
        this.alertaService.error('No se ha podido actualizar tu documento, intenta nuevamente');
      }
    );
  }

  borrar(documento: any) {
    if (documento.usuarioDocumentoId) {
      this.genericService.sendDeleteRequest(`${environment.usuarioDocumentos}/${documento.usuarioDocumentoId}`).subscribe(
        (response: any) => {
          documento.image = null;
          this.alertaService.success('Tu documento se eliminó correctamente');
        },
        (error: HttpErrorResponse) => {
          this.alertaService.error('No se ha podido borrar tu documento, intenta nuevamente');
        }
      );
    } else {
      this.alertaService.warn('Aún no has cargado un documento');
    }
  }

  getBase64(file, componente, documentoto: any) {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function() {
      componente.photo_url = reader.result;
      documentoto.image = reader.result;
    };
    reader.onerror = function(error) {};
  }

  changeFile(evt: any, documento: any) {
    let files = evt.target.files;
    let file = files[0];
    this.getBase64(file, this, documento);

    //setTimeout((documento.image = this.photo_url), 500);

    //    this.onlyPhoto = true;
  }
}
