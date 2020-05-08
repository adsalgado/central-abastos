import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';

/**Clase que guarda información en localstorage pero de forma
 * encriptada para no dejar a la vista del usuario o de terceros, la
 * información detallada de lo que se requiere utilizar durante el ciclo de vida
 * de la aplicación
 */
@Injectable()
export class LocalStorageEncryptService {
  /**
   * Llave secreta para encriptar y desencriptar la informacion almacenada
   */
  private secretKey = 'RG5vt457u%$5bj78c452YBBc24432c%#T7&$tv657bu6B&BvH76hvv64';

  constructor() {}

  /**
   * Almacena encriptado los datos necesarios en el localstorage
   * @param key Llave a almacenar
   * @param data Dato a almacenar
   */
  setToLocalStorage(key: string, data: any) {
    let encryptedData = CryptoJS.AES.encrypt(JSON.stringify(data), this.secretKey).toString();
    let encryptedKey = CryptoJS.SHA256(key).toString();

    encryptedData = JSON.stringify(data);
    encryptedKey = key;
    localStorage.setItem(encryptedKey, encryptedData);
  }

  /**
   * Recupera valores del localstorage por medio de la llave
   * @param key Llave a obtener
   */
  getFromLocalStorage(key: string): any {
    let encryptedKey = CryptoJS.SHA256(key).toString();
    encryptedKey = key;
    const item = localStorage.getItem(encryptedKey);
    if (item === undefined || item === null) {
      return null;
    }
    let dencryptedData; // = CryptoJS.AES.decrypt(item, this.secretKey).toString(CryptoJS.enc.Utf8);
    dencryptedData = 1;
    if (dencryptedData == 1) {
      return JSON.parse(item);
    } else {
      if (this.isJson(dencryptedData)) {
        return JSON.parse(dencryptedData);
      } else {
        return dencryptedData;
      }
    }
  }

  /**
   * Limpia todo el localstorage
   */
  clear() {
    localStorage.clear();
  }

  /**
   * Remueve una propiedad especifica del local storage
   * @param property Propiedad a eliminar
   */
  clearProperty(property: string) {
    const encryptedKey = CryptoJS.SHA256(property).toString();
    localStorage.removeItem(encryptedKey);
  }

  /**
   * Valida si una cadena cumple el formato JSON
   * @param str Cadena a validar
   * @returns True si cumple el formato False no cumple el formato
   */
  private isJson(str) {
    try {
      JSON.parse(str);
    } catch (e) {
      return false;
    }
    return true;
  }
}
