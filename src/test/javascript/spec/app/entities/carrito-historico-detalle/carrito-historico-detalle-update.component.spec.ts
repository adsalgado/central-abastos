/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { CarritoHistoricoDetalleUpdateComponent } from 'app/entities/carrito-historico-detalle/carrito-historico-detalle-update.component';
import { CarritoHistoricoDetalleService } from 'app/entities/carrito-historico-detalle/carrito-historico-detalle.service';
import { CarritoHistoricoDetalle } from 'app/shared/model/carrito-historico-detalle.model';

describe('Component Tests', () => {
  describe('CarritoHistoricoDetalle Management Update Component', () => {
    let comp: CarritoHistoricoDetalleUpdateComponent;
    let fixture: ComponentFixture<CarritoHistoricoDetalleUpdateComponent>;
    let service: CarritoHistoricoDetalleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [CarritoHistoricoDetalleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CarritoHistoricoDetalleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CarritoHistoricoDetalleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarritoHistoricoDetalleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CarritoHistoricoDetalle(123);
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
        const entity = new CarritoHistoricoDetalle();
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
