/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AbastosTestModule } from '../../../test.module';
import { InventarioHistoricoDeleteDialogComponent } from 'app/entities/inventario-historico/inventario-historico-delete-dialog.component';
import { InventarioHistoricoService } from 'app/entities/inventario-historico/inventario-historico.service';

describe('Component Tests', () => {
  describe('InventarioHistorico Management Delete Component', () => {
    let comp: InventarioHistoricoDeleteDialogComponent;
    let fixture: ComponentFixture<InventarioHistoricoDeleteDialogComponent>;
    let service: InventarioHistoricoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [InventarioHistoricoDeleteDialogComponent]
      })
        .overrideTemplate(InventarioHistoricoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InventarioHistoricoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventarioHistoricoService);
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
