/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AbastosTestModule } from '../../../test.module';
import { CarritoHistoricoDeleteDialogComponent } from 'app/entities/carrito-historico/carrito-historico-delete-dialog.component';
import { CarritoHistoricoService } from 'app/entities/carrito-historico/carrito-historico.service';

describe('Component Tests', () => {
  describe('CarritoHistorico Management Delete Component', () => {
    let comp: CarritoHistoricoDeleteDialogComponent;
    let fixture: ComponentFixture<CarritoHistoricoDeleteDialogComponent>;
    let service: CarritoHistoricoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [CarritoHistoricoDeleteDialogComponent]
      })
        .overrideTemplate(CarritoHistoricoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CarritoHistoricoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarritoHistoricoService);
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
