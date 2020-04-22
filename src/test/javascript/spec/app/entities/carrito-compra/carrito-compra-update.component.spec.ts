/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { CarritoCompraUpdateComponent } from 'app/entities/carrito-compra/carrito-compra-update.component';
import { CarritoCompraService } from 'app/entities/carrito-compra/carrito-compra.service';
import { CarritoCompra } from 'app/shared/model/carrito-compra.model';

describe('Component Tests', () => {
  describe('CarritoCompra Management Update Component', () => {
    let comp: CarritoCompraUpdateComponent;
    let fixture: ComponentFixture<CarritoCompraUpdateComponent>;
    let service: CarritoCompraService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [CarritoCompraUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CarritoCompraUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CarritoCompraUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarritoCompraService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CarritoCompra(123);
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
        const entity = new CarritoCompra();
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
