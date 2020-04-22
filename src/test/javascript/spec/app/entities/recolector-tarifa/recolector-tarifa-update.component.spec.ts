/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { RecolectorTarifaUpdateComponent } from 'app/entities/recolector-tarifa/recolector-tarifa-update.component';
import { RecolectorTarifaService } from 'app/entities/recolector-tarifa/recolector-tarifa.service';
import { RecolectorTarifa } from 'app/shared/model/recolector-tarifa.model';

describe('Component Tests', () => {
  describe('RecolectorTarifa Management Update Component', () => {
    let comp: RecolectorTarifaUpdateComponent;
    let fixture: ComponentFixture<RecolectorTarifaUpdateComponent>;
    let service: RecolectorTarifaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [RecolectorTarifaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RecolectorTarifaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecolectorTarifaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecolectorTarifaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RecolectorTarifa(123);
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
        const entity = new RecolectorTarifa();
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
