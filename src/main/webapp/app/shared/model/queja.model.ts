export interface IQueja {
  id?: number;
}

export class Queja implements IQueja {
  constructor(public id?: number) {}
}
