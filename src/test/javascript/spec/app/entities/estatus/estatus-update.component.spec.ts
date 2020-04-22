/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { EstatusUpdateComponent } from 'app/entities/estatus/estatus-update.component';
import { EstatusService } from 'app/entities/estatus/estatus.service';
import { Estatus } from 'app/shared/model/estatus.model';

describe('Component Tests', () => {
  describe('Estatus Management Update Component', () => {
    let comp: EstatusUpdateComponent;
    let fixture: ComponentFixture<EstatusUpdateComponent>;
    let service: EstatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [EstatusUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EstatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Estatus(123);
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
        const entity = new Estatus();
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
