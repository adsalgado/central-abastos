import { Moment } from 'moment';

export interface IChatPrivate {
  id?: number;
  message?: string;
  user?: string;
  fecha?: Moment;
}

export class ChatPrivate implements IChatPrivate {
  constructor(public id?: number, public message?: string, public user?: string, public fecha?: Moment) {}
}
