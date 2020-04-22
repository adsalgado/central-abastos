/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AbastosTestModule } from '../../../test.module';
import { CarritoHistoricoDetalleDeleteDialogComponent } from 'app/entities/carrito-historico-detalle/carrito-historico-detalle-delete-dialog.component';
import { CarritoHistoricoDetalleService } from 'app/entities/carrito-historico-detalle/carrito-historico-detalle.service';

describe('Component Tests', () => {
  describe('CarritoHistoricoDetalle Management Delete Component', () => {
    let comp: CarritoHistoricoDetalleDeleteDialogComponent;
    let fixture: ComponentFixture<CarritoHistoricoDetalleDeleteDialogComponent>;
    let service: CarritoHistoricoDetalleService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [CarritoHistoricoDetalleDeleteDialogComponent]
      })
        .overrideTemplate(CarritoHistoricoDetalleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CarritoHistoricoDetalleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarritoHistoricoDetalleService);
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
