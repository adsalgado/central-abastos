import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChatPrivate } from 'app/shared/model/chat-private.model';

@Component({
  selector: 'jhi-chat-private-detail',
  templateUrl: './chat-private-detail.component.html'
})
export class ChatPrivateDetailComponent implements OnInit {
  chatPrivate: IChatPrivate;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ chatPrivate }) => {
      this.chatPrivate = chatPrivate;
    });
  }

  previousState() {
    window.history.back();
  }
}
