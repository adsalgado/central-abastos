export interface IParametrosAplicacion {
  id?: number;
  clave?: string;
  descripcion?: string;
  adjuntoId?: number;
}

export class ParametrosAplicacion implements IParametrosAplicacion {
  constructor(public id?: number, public clave?: string, public descripcion?: string, public adjuntoId?: number) {}
}
