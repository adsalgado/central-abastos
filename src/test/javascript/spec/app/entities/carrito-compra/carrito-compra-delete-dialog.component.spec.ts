/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AbastosTestModule } from '../../../test.module';
import { CarritoCompraDeleteDialogComponent } from 'app/entities/carrito-compra/carrito-compra-delete-dialog.component';
import { CarritoCompraService } from 'app/entities/carrito-compra/carrito-compra.service';

describe('Component Tests', () => {
  describe('CarritoCompra Management Delete Component', () => {
    let comp: CarritoCompraDeleteDialogComponent;
    let fixture: ComponentFixture<CarritoCompraDeleteDialogComponent>;
    let service: CarritoCompraService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [CarritoCompraDeleteDialogComponent]
      })
        .overrideTemplate(CarritoCompraDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CarritoCompraDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarritoCompraService);
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
