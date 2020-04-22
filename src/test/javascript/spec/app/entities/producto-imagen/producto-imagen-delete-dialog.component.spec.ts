/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AbastosTestModule } from '../../../test.module';
import { ProductoImagenDeleteDialogComponent } from 'app/entities/producto-imagen/producto-imagen-delete-dialog.component';
import { ProductoImagenService } from 'app/entities/producto-imagen/producto-imagen.service';

describe('Component Tests', () => {
  describe('ProductoImagen Management Delete Component', () => {
    let comp: ProductoImagenDeleteDialogComponent;
    let fixture: ComponentFixture<ProductoImagenDeleteDialogComponent>;
    let service: ProductoImagenService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [ProductoImagenDeleteDialogComponent]
      })
        .overrideTemplate(ProductoImagenDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductoImagenDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductoImagenService);
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
