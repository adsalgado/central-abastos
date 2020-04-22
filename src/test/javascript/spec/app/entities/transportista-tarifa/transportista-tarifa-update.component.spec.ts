/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { TransportistaTarifaUpdateComponent } from 'app/entities/transportista-tarifa/transportista-tarifa-update.component';
import { TransportistaTarifaService } from 'app/entities/transportista-tarifa/transportista-tarifa.service';
import { TransportistaTarifa } from 'app/shared/model/transportista-tarifa.model';

describe('Component Tests', () => {
  describe('TransportistaTarifa Management Update Component', () => {
    let comp: TransportistaTarifaUpdateComponent;
    let fixture: ComponentFixture<TransportistaTarifaUpdateComponent>;
    let service: TransportistaTarifaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [TransportistaTarifaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TransportistaTarifaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransportistaTarifaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransportistaTarifaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransportistaTarifa(123);
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
        const entity = new TransportistaTarifa();
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
