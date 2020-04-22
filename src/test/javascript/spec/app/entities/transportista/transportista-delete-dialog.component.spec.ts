/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AbastosTestModule } from '../../../test.module';
import { TransportistaDeleteDialogComponent } from 'app/entities/transportista/transportista-delete-dialog.component';
import { TransportistaService } from 'app/entities/transportista/transportista.service';

describe('Component Tests', () => {
  describe('Transportista Management Delete Component', () => {
    let comp: TransportistaDeleteDialogComponent;
    let fixture: ComponentFixture<TransportistaDeleteDialogComponent>;
    let service: TransportistaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [TransportistaDeleteDialogComponent]
      })
        .overrideTemplate(TransportistaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransportistaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransportistaService);
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
