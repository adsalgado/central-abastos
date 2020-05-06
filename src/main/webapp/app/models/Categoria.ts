/*
 *Categoria Model.
 */
export class Categoria {
  /** Generate Categoria */
  constructor(public id: number, public nombre: string, public empresaId: number) {}

  /*
   *Función para obtener información.
   */
  static fromJson(data: any) {
    if (!data) {
      throw new Error('Invalid argument');
    }

    let temp: Categoria = new Categoria(data.id, data.nombre, data.empresaId);

    return temp;
  }
}
