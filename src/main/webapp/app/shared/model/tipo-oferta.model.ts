import { IOfertaProveedor } from 'app/shared/model/oferta-proveedor.model';

export interface ITipoOferta {
  id?: number;
  nombre?: string;
  descripcion?: string;
  ofertaProveedors?: IOfertaProveedor[];
}

export class TipoOferta implements ITipoOferta {
  constructor(public id?: number, public nombre?: string, public descripcion?: string, public ofertaProveedors?: IOfertaProveedor[]) {}
}
