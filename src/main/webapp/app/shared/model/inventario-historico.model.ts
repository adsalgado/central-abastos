import { Moment } from 'moment';

export const enum TipoMovimiento {
  ENTRADA = 'ENTRADA',
  SALIDA = 'SALIDA'
}

export interface IInventarioHistorico {
  id?: number;
  tipoMovimiento?: TipoMovimiento;
  cantidad?: number;
  totalAnterior?: number;
  totalFinal?: number;
  fechaMovimiento?: Moment;
  usuarioMovimientoId?: number;
  inventarioId?: number;
}

export class InventarioHistorico implements IInventarioHistorico {
  constructor(
    public id?: number,
    public tipoMovimiento?: TipoMovimiento,
    public cantidad?: number,
    public totalAnterior?: number,
    public totalFinal?: number,
    public fechaMovimiento?: Moment,
    public usuarioMovimientoId?: number,
    public inventarioId?: number
  ) {}
}
