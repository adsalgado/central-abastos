/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { RecolectorUpdateComponent } from 'app/entities/recolector/recolector-update.component';
import { RecolectorService } from 'app/entities/recolector/recolector.service';
import { Recolector } from 'app/shared/model/recolector.model';

describe('Component Tests', () => {
  describe('Recolector Management Update Component', () => {
    let comp: RecolectorUpdateComponent;
    let fixture: ComponentFixture<RecolectorUpdateComponent>;
    let service: RecolectorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [RecolectorUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RecolectorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecolectorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecolectorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Recolector(123);
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
        const entity = new Recolector();
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
