/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { ProductoImagenUpdateComponent } from 'app/entities/producto-imagen/producto-imagen-update.component';
import { ProductoImagenService } from 'app/entities/producto-imagen/producto-imagen.service';
import { ProductoImagen } from 'app/shared/model/producto-imagen.model';

describe('Component Tests', () => {
  describe('ProductoImagen Management Update Component', () => {
    let comp: ProductoImagenUpdateComponent;
    let fixture: ComponentFixture<ProductoImagenUpdateComponent>;
    let service: ProductoImagenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [ProductoImagenUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductoImagenUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductoImagenUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductoImagenService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductoImagen(123);
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
        const entity = new ProductoImagen();
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
