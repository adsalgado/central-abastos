/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AbastosTestModule } from '../../../test.module';
import { AdjuntoDeleteDialogComponent } from 'app/entities/adjunto/adjunto-delete-dialog.component';
import { AdjuntoService } from 'app/entities/adjunto/adjunto.service';

describe('Component Tests', () => {
  describe('Adjunto Management Delete Component', () => {
    let comp: AdjuntoDeleteDialogComponent;
    let fixture: ComponentFixture<AdjuntoDeleteDialogComponent>;
    let service: AdjuntoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [AdjuntoDeleteDialogComponent]
      })
        .overrideTemplate(AdjuntoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AdjuntoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdjuntoService);
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
