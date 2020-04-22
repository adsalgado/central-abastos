/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { OfertaProveedorUpdateComponent } from 'app/entities/oferta-proveedor/oferta-proveedor-update.component';
import { OfertaProveedorService } from 'app/entities/oferta-proveedor/oferta-proveedor.service';
import { OfertaProveedor } from 'app/shared/model/oferta-proveedor.model';

describe('Component Tests', () => {
  describe('OfertaProveedor Management Update Component', () => {
    let comp: OfertaProveedorUpdateComponent;
    let fixture: ComponentFixture<OfertaProveedorUpdateComponent>;
    let service: OfertaProveedorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [OfertaProveedorUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OfertaProveedorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OfertaProveedorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OfertaProveedorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OfertaProveedor(123);
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
        const entity = new OfertaProveedor();
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
