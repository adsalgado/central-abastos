/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AbastosTestModule } from '../../../test.module';
import { RecolectorTarifaDeleteDialogComponent } from 'app/entities/recolector-tarifa/recolector-tarifa-delete-dialog.component';
import { RecolectorTarifaService } from 'app/entities/recolector-tarifa/recolector-tarifa.service';

describe('Component Tests', () => {
  describe('RecolectorTarifa Management Delete Component', () => {
    let comp: RecolectorTarifaDeleteDialogComponent;
    let fixture: ComponentFixture<RecolectorTarifaDeleteDialogComponent>;
    let service: RecolectorTarifaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [RecolectorTarifaDeleteDialogComponent]
      })
        .overrideTemplate(RecolectorTarifaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecolectorTarifaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecolectorTarifaService);
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
