/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AbastosTestModule } from '../../../test.module';
import { TransportistaTarifaDeleteDialogComponent } from 'app/entities/transportista-tarifa/transportista-tarifa-delete-dialog.component';
import { TransportistaTarifaService } from 'app/entities/transportista-tarifa/transportista-tarifa.service';

describe('Component Tests', () => {
  describe('TransportistaTarifa Management Delete Component', () => {
    let comp: TransportistaTarifaDeleteDialogComponent;
    let fixture: ComponentFixture<TransportistaTarifaDeleteDialogComponent>;
    let service: TransportistaTarifaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [TransportistaTarifaDeleteDialogComponent]
      })
        .overrideTemplate(TransportistaTarifaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransportistaTarifaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransportistaTarifaService);
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
