export interface IDireccion {
  id?: number;
  direccion?: string;
  colonia?: string;
  codigoPostal?: string;
  geolocalizacion?: string;
  clienteId?: number;
}

export class Direccion implements IDireccion {
  constructor(
    public id?: number,
    public direccion?: string,
    public colonia?: string,
    public codigoPostal?: string,
    public geolocalizacion?: string,
    public clienteId?: number
  ) {}
}
