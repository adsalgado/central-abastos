export interface IAbastosResponse {
  error?: number;
  messageError?: string;
  data?: any;
}

export class AbastosResponse implements IAbastosResponse {
  constructor(public error?: number, public messageError?: string, public data?: any) {}
}
