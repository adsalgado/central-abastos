/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { QuejaUpdateComponent } from 'app/entities/queja/queja-update.component';
import { QuejaService } from 'app/entities/queja/queja.service';
import { Queja } from 'app/shared/model/queja.model';

describe('Component Tests', () => {
  describe('Queja Management Update Component', () => {
    let comp: QuejaUpdateComponent;
    let fixture: ComponentFixture<QuejaUpdateComponent>;
    let service: QuejaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [QuejaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(QuejaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuejaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuejaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Queja(123);
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
        const entity = new Queja();
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
