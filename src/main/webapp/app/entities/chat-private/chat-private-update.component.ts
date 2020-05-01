import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IChatPrivate, ChatPrivate } from 'app/shared/model/chat-private.model';
import { ChatPrivateService } from './chat-private.service';

@Component({
  selector: 'jhi-chat-private-update',
  templateUrl: './chat-private-update.component.html'
})
export class ChatPrivateUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    message: [null, [Validators.required]],
    user: [null, [Validators.required, Validators.minLength(5)]],
    fecha: []
  });

  constructor(protected chatPrivateService: ChatPrivateService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ chatPrivate }) => {
      this.updateForm(chatPrivate);
    });
  }

  updateForm(chatPrivate: IChatPrivate) {
    this.editForm.patchValue({
      id: chatPrivate.id,
      message: chatPrivate.message,
      user: chatPrivate.user,
      fecha: chatPrivate.fecha != null ? chatPrivate.fecha.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const chatPrivate = this.createFromForm();
    if (chatPrivate.id !== undefined) {
      this.subscribeToSaveResponse(this.chatPrivateService.update(chatPrivate));
    } else {
      this.subscribeToSaveResponse(this.chatPrivateService.create(chatPrivate));
    }
  }

  private createFromForm(): IChatPrivate {
    return {
      ...new ChatPrivate(),
      id: this.editForm.get(['id']).value,
      message: this.editForm.get(['message']).value,
      user: this.editForm.get(['user']).value,
      fecha: this.editForm.get(['fecha']).value != null ? moment(this.editForm.get(['fecha']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChatPrivate>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
