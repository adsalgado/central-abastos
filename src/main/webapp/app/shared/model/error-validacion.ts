export interface IErrorValidacion {
  row?: number;
  field?: string;
  errorMessage?: string;
}

export class ErrorValidacion implements IErrorValidacion {
  constructor(public row?: number, public field?: string, public errorMessage?: string) {}
}
