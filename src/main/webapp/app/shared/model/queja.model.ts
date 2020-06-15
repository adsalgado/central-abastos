import { TrackingQueja } from 'app/models/tracking-queja';

export interface UserTmp {
  fullName: String;
  profile: String;
}

export interface IQueja {
  claimId?: Number;
  createdAt?: Date;
  lastUpdated?: Date;
  tracking?: TrackingQueja[];
  status?: String;
  requestedBy?: UserTmp;
  claimMessage?: String;
  numeroPedido?: Number;
  evidence?: String;
}

export class Queja implements IQueja {
  constructor(
    public claimId?: number,
    public createdAt?: Date,
    public lastUpdated?: Date,
    public tracking?: TrackingQueja[],
    public status?: String,
    public requestedBy?: UserTmp,
    public claimMessage?: String,
    public numeroPedido?: Number,
    public evidence?: String
  ) {}
}
