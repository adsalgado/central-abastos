import { Injectable } from '@angular/core';
import swal, { SweetAlertOptions } from 'sweetalert2';

@Injectable()
export class AlertService {
  //confirm: any;
  static titleSuccess: string = 'Success';
  static titleWarn: string = 'Warn';
  static titleInfo: string = 'Info';
  static titleError: string = 'Error!';

  success(message: string) {
    swal.fire(AlertService.titleSuccess, message, 'success');
  }

  error(message: string) {
    swal.fire({
      type: 'error',
      title: AlertService.titleError,
      text: message
      //text: 'Something went wrong!',
      //footer: '<a href>Why do I have this issue?</a>'
    });
  }

  errorAlertGeneric(message: string) {
    swal.fire({
      type: 'error',
      title: AlertService.titleError,
      text: message
      //text: 'Something went wrong!',
      //footer: '<a href>Why do I have this issue?</a>'
    });
  }

  info(message: string) {
    swal.fire({
      type: 'info',
      title: AlertService.titleInfo,
      text: message
      //footer: '<a href>Why do I have this issue?</a>'
    });
    /*swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            type: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            this.confirm = result;
            if (result.value) {
                swal.fire(
                    'Deleted!',
                    'Your file has been deleted.',
                    'success'
                )
            }
        })*/
  }

  warn(message: string) {
    swal.fire({
      type: 'warning',
      title: AlertService.titleWarn,
      text: message
      //footer: '<a href>Why do I have this issue?</a>'
    });
  }

  confirm(data: any) {
    return swal.fire({
      title: data.title,
      text: data.text,
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: data.confirmText,
      cancelButtonText: 'Cancelar'
    });
    /*.then((result) => {
          if (result.value) {
            swal.fire(
              'Deleted!',
              'Your file has been deleted.',
              'success'
            )
          }
        })*/
  }
}
