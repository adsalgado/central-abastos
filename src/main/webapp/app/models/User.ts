/*
 *User Model.
 */
export class User {
  /** Generate User */
  constructor(public id_token: string, public username: string, public tipo_usuario: number, public parametros: any) {}

  /*
   *Función para obtener información.
   */
  static fromJson(data: any) {
    if (!data) {
      throw new Error('Invalid argument');
    }

    let temp: User = new User(data.id_token, data.username, data.tipo_usuario, data.parametros);

    return temp;
  }
}
