/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AbastosTestModule } from '../../../test.module';
import { HistoricoPedidoDeleteDialogComponent } from 'app/entities/historico-pedido/historico-pedido-delete-dialog.component';
import { HistoricoPedidoService } from 'app/entities/historico-pedido/historico-pedido.service';

describe('Component Tests', () => {
  describe('HistoricoPedido Management Delete Component', () => {
    let comp: HistoricoPedidoDeleteDialogComponent;
    let fixture: ComponentFixture<HistoricoPedidoDeleteDialogComponent>;
    let service: HistoricoPedidoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [HistoricoPedidoDeleteDialogComponent]
      })
        .overrideTemplate(HistoricoPedidoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HistoricoPedidoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HistoricoPedidoService);
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
