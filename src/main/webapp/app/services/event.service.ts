import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable()
export class Event {
  private subjects: any[] = [];

  get(topic: string): Observable<any[]> {
    let subject = new Subject<any[]>();
    if (!this.subjects[topic]) {
      this.subjects[topic] = {
        subject: subject,
        data: null
      };
    }
    console.log(this.subjects[topic]);

    return this.subjects[topic].subject.asObservable();
  }

  publish(topic: string, data: any = {}) {
    let subject = new Subject<any[]>();
    subject.next(data);
    subject.complete();
    this.subjects[topic] = {
      subject: subject,
      data: data
    };
    console.log(this.subjects);
  }
}
