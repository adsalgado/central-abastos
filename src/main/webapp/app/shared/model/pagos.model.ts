export interface IPagos {
  id?: number;
}

export class Pagos implements IPagos {
  constructor(public id?: number) {}
}
