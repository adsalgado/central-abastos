import { User } from './User';
import { Queja } from 'app/shared/model/queja.model';

export interface TrackingQueja {
  trackingDate?: Date;
  trackingMessage?: String;
  user?: User;
  quejaId?: Number;
}

export class TrackingQueja implements TrackingQueja {
  constructor(public trackingDate?: Date, public trackingMessage?: String, public user?: User, public quejaId?: Number) {}
}
