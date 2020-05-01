import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChatPrivate } from 'app/shared/model/chat-private.model';
import { ChatPrivateService } from './chat-private.service';

@Component({
  selector: 'jhi-chat-private-delete-dialog',
  templateUrl: './chat-private-delete-dialog.component.html'
})
export class ChatPrivateDeleteDialogComponent {
  chatPrivate: IChatPrivate;

  constructor(
    protected chatPrivateService: ChatPrivateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.chatPrivateService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'chatPrivateListModification',
        content: 'Deleted an chatPrivate'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-chat-private-delete-popup',
  template: ''
})
export class ChatPrivateDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ chatPrivate }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ChatPrivateDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.chatPrivate = chatPrivate;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/chat-private', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/chat-private', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
