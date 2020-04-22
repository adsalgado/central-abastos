/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { UsuarioImagenUpdateComponent } from 'app/entities/usuario-imagen/usuario-imagen-update.component';
import { UsuarioImagenService } from 'app/entities/usuario-imagen/usuario-imagen.service';
import { UsuarioImagen } from 'app/shared/model/usuario-imagen.model';

describe('Component Tests', () => {
  describe('UsuarioImagen Management Update Component', () => {
    let comp: UsuarioImagenUpdateComponent;
    let fixture: ComponentFixture<UsuarioImagenUpdateComponent>;
    let service: UsuarioImagenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [UsuarioImagenUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UsuarioImagenUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UsuarioImagenUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UsuarioImagenService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UsuarioImagen(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new UsuarioImagen();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
