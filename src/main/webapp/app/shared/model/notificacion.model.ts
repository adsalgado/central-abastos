export interface INotificacion {
  id?: number;
}

export class Notificacion implements INotificacion {
  constructor(public id?: number) {}
}
