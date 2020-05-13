import { JhiEventManager } from 'ng-jhipster';
import { Component, OnDestroy } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnDestroy {
  title = 'basic-with-login';

  public eventoLogin: any = null;
  constructor(private eventManager: JhiEventManager) {
    console.log('app ejecutandose');
    this.eventoLogin = this.eventManager.subscribe('startSession', response => {
      console.log('logica login');
    });
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventoLogin);
  }
}
