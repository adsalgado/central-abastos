/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { PagosUpdateComponent } from 'app/entities/pagos/pagos-update.component';
import { PagosService } from 'app/entities/pagos/pagos.service';
import { Pagos } from 'app/shared/model/pagos.model';

describe('Component Tests', () => {
  describe('Pagos Management Update Component', () => {
    let comp: PagosUpdateComponent;
    let fixture: ComponentFixture<PagosUpdateComponent>;
    let service: PagosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [PagosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PagosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PagosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PagosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pagos(123);
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
        const entity = new Pagos();
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
