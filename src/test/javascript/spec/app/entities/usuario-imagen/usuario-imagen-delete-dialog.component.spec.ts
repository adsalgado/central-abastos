/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AbastosTestModule } from '../../../test.module';
import { UsuarioImagenDeleteDialogComponent } from 'app/entities/usuario-imagen/usuario-imagen-delete-dialog.component';
import { UsuarioImagenService } from 'app/entities/usuario-imagen/usuario-imagen.service';

describe('Component Tests', () => {
  describe('UsuarioImagen Management Delete Component', () => {
    let comp: UsuarioImagenDeleteDialogComponent;
    let fixture: ComponentFixture<UsuarioImagenDeleteDialogComponent>;
    let service: UsuarioImagenService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [UsuarioImagenDeleteDialogComponent]
      })
        .overrideTemplate(UsuarioImagenDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UsuarioImagenDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UsuarioImagenService);
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
