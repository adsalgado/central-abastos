import { User } from 'app/core/user/user.model';

export interface TrackingQueja {
  trackingDate?: Date;
  trackingMessage?: String;
  user?: User;
  quejaId?: Number;
}

export class TrackingQueja implements TrackingQueja {
  public isNew: Boolean = false;
  constructor(public trackingDate?: Date, public trackingMessage?: String, public user?: User, public quejaId?: Number) {}
}
