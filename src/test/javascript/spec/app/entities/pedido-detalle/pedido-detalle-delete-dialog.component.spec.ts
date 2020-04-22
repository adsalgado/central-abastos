/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AbastosTestModule } from '../../../test.module';
import { PedidoDetalleDeleteDialogComponent } from 'app/entities/pedido-detalle/pedido-detalle-delete-dialog.component';
import { PedidoDetalleService } from 'app/entities/pedido-detalle/pedido-detalle.service';

describe('Component Tests', () => {
  describe('PedidoDetalle Management Delete Component', () => {
    let comp: PedidoDetalleDeleteDialogComponent;
    let fixture: ComponentFixture<PedidoDetalleDeleteDialogComponent>;
    let service: PedidoDetalleService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [PedidoDetalleDeleteDialogComponent]
      })
        .overrideTemplate(PedidoDetalleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PedidoDetalleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PedidoDetalleService);
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
