export interface IRecolectorTarifa {
  id?: number;
  rangoMinimo?: number;
  rangoMaximo?: number;
  precio?: number;
  recolectorId?: number;
}

export class RecolectorTarifa implements IRecolectorTarifa {
  constructor(
    public id?: number,
    public rangoMinimo?: number,
    public rangoMaximo?: number,
    public precio?: number,
    public recolectorId?: number
  ) {}
}
