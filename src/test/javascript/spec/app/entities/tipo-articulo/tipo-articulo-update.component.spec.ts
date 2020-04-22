/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { TipoArticuloUpdateComponent } from 'app/entities/tipo-articulo/tipo-articulo-update.component';
import { TipoArticuloService } from 'app/entities/tipo-articulo/tipo-articulo.service';
import { TipoArticulo } from 'app/shared/model/tipo-articulo.model';

describe('Component Tests', () => {
  describe('TipoArticulo Management Update Component', () => {
    let comp: TipoArticuloUpdateComponent;
    let fixture: ComponentFixture<TipoArticuloUpdateComponent>;
    let service: TipoArticuloService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [TipoArticuloUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoArticuloUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoArticuloUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoArticuloService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoArticulo(123);
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
        const entity = new TipoArticulo();
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
