/*
 *Seccion Model.
 */
export class Seccion {
  /** Generate Seccion */
  constructor(public id: number, public nombre: string, public empresaId: number) {}

  /*
   *Función para obtener información.
   */
  static fromJson(data: any) {
    if (!data) {
      throw new Error('Invalid argument');
    }

    let temp: Seccion = new Seccion(data.id, data.nombre, data.empresaId);

    return temp;
  }
}
