/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { AdjuntoUpdateComponent } from 'app/entities/adjunto/adjunto-update.component';
import { AdjuntoService } from 'app/entities/adjunto/adjunto.service';
import { Adjunto } from 'app/shared/model/adjunto.model';

describe('Component Tests', () => {
  describe('Adjunto Management Update Component', () => {
    let comp: AdjuntoUpdateComponent;
    let fixture: ComponentFixture<AdjuntoUpdateComponent>;
    let service: AdjuntoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [AdjuntoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AdjuntoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdjuntoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdjuntoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Adjunto(123);
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
        const entity = new Adjunto();
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
