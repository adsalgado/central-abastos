/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { TipoOfertaUpdateComponent } from 'app/entities/tipo-oferta/tipo-oferta-update.component';
import { TipoOfertaService } from 'app/entities/tipo-oferta/tipo-oferta.service';
import { TipoOferta } from 'app/shared/model/tipo-oferta.model';

describe('Component Tests', () => {
  describe('TipoOferta Management Update Component', () => {
    let comp: TipoOfertaUpdateComponent;
    let fixture: ComponentFixture<TipoOfertaUpdateComponent>;
    let service: TipoOfertaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [TipoOfertaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoOfertaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoOfertaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoOfertaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoOferta(123);
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
        const entity = new TipoOferta();
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
