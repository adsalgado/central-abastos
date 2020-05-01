/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AbastosTestModule } from '../../../test.module';
import { ChatPrivateDeleteDialogComponent } from 'app/entities/chat-private/chat-private-delete-dialog.component';
import { ChatPrivateService } from 'app/entities/chat-private/chat-private.service';

describe('Component Tests', () => {
  describe('ChatPrivate Management Delete Component', () => {
    let comp: ChatPrivateDeleteDialogComponent;
    let fixture: ComponentFixture<ChatPrivateDeleteDialogComponent>;
    let service: ChatPrivateService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [ChatPrivateDeleteDialogComponent]
      })
        .overrideTemplate(ChatPrivateDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChatPrivateDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChatPrivateService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
