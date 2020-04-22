/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { ParametrosAplicacionUpdateComponent } from 'app/entities/parametros-aplicacion/parametros-aplicacion-update.component';
import { ParametrosAplicacionService } from 'app/entities/parametros-aplicacion/parametros-aplicacion.service';
import { ParametrosAplicacion } from 'app/shared/model/parametros-aplicacion.model';

describe('Component Tests', () => {
  describe('ParametrosAplicacion Management Update Component', () => {
    let comp: ParametrosAplicacionUpdateComponent;
    let fixture: ComponentFixture<ParametrosAplicacionUpdateComponent>;
    let service: ParametrosAplicacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [ParametrosAplicacionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ParametrosAplicacionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParametrosAplicacionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParametrosAplicacionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ParametrosAplicacion(123);
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
        const entity = new ParametrosAplicacion();
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
