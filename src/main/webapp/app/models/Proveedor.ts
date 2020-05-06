/*
 *Proveedor Model.
 */
export class Proveedor {
  /** Generate Proveedor */
  constructor(public id: number, public nombre: string, public empresaId: number) {}

  /*
   *Función para obtener información.
   */
  static fromJson(data: any) {
    if (!data) {
      throw new Error('Invalid argument');
    }

    let temp: Proveedor = new Proveedor(data.id, data.nombre, data.empresaId);

    return temp;
  }
}
