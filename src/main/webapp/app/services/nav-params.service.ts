import { Injectable } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LocalStorageEncryptService } from './local-storage-encrypt-service';
import { Component } from '@angular/compiler/src/core';

@Injectable()
export class NavParamsService {
  private params: any = {};
  private key: string = 'params';
  constructor(private router: Router, private routes: ActivatedRoute, private localStorageEncryptService: LocalStorageEncryptService) {}

  simpleStringify(object) {
    var simpleObject = {};
    for (var prop in object) {
      if (!object.hasOwnProperty(prop)) {
        continue;
      }
      if (typeof object[prop] == 'object') {
        continue;
      }
      if (typeof object[prop] == 'function') {
        continue;
      }
      simpleObject[prop] = object[prop];
    }
    return JSON.stringify(simpleObject); // returns cleaned up JSON
  }

  public push(routerLink: string, p: any = null) {
    /**
     * Format params (Toma en cuenta que no se debe repetir el nombre de propiedad o se sobreescribirá)
     * params = {propertyName: "propiedad" , value: "valor", etc: "etc"};
     */
    console.log(p);

    if (p) {
      let result = Object.keys(p).map(function(key) {
        return [key, p[key]];
      });
      console.log(this.params);

      result.forEach(element => {
        this.params[element[0]] = JSON.stringify(element[1]);

        //se envían parámetros al objeto general
      });

      console.log(this.params);
    }
    this.localStorageEncryptService.setToLocalStorage(this.key, this.params);
    console.log(routerLink);

    this.router.navigate([routerLink]);
  }

  public get(key: string) {
    this.params = this.localStorageEncryptService.getFromLocalStorage(this.key);
    let valor: any = Object.values(this.params)[Object.keys(this.params).indexOf(key)];
    if (valor) {
      return JSON.parse(valor);
    } else {
      return null;
    }
  }

  public deletePropertie(propiedad: string) {
    if (this.params) {
      delete this.params[propiedad];
    }
  }
}
