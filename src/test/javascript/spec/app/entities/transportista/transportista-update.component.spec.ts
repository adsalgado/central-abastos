/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { TransportistaUpdateComponent } from 'app/entities/transportista/transportista-update.component';
import { TransportistaService } from 'app/entities/transportista/transportista.service';
import { Transportista } from 'app/shared/model/transportista.model';

describe('Component Tests', () => {
  describe('Transportista Management Update Component', () => {
    let comp: TransportistaUpdateComponent;
    let fixture: ComponentFixture<TransportistaUpdateComponent>;
    let service: TransportistaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [TransportistaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TransportistaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransportistaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransportistaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Transportista(123);
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
        const entity = new Transportista();
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
