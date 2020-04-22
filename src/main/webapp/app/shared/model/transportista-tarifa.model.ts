export interface ITransportistaTarifa {
  id?: number;
  rangoMinimo?: number;
  rangoMaximo?: number;
  precio?: number;
  transportistaId?: number;
}

export class TransportistaTarifa implements ITransportistaTarifa {
  constructor(
    public id?: number,
    public rangoMinimo?: number,
    public rangoMaximo?: number,
    public precio?: number,
    public transportistaId?: number
  ) {}
}
