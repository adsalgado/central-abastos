/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AbastosTestModule } from '../../../test.module';
import { PagosDeleteDialogComponent } from 'app/entities/pagos/pagos-delete-dialog.component';
import { PagosService } from 'app/entities/pagos/pagos.service';

describe('Component Tests', () => {
  describe('Pagos Management Delete Component', () => {
    let comp: PagosDeleteDialogComponent;
    let fixture: ComponentFixture<PagosDeleteDialogComponent>;
    let service: PagosService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [PagosDeleteDialogComponent]
      })
        .overrideTemplate(PagosDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PagosDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PagosService);
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
