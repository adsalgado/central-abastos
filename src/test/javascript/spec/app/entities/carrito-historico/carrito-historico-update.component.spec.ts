/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { CarritoHistoricoUpdateComponent } from 'app/entities/carrito-historico/carrito-historico-update.component';
import { CarritoHistoricoService } from 'app/entities/carrito-historico/carrito-historico.service';
import { CarritoHistorico } from 'app/shared/model/carrito-historico.model';

describe('Component Tests', () => {
  describe('CarritoHistorico Management Update Component', () => {
    let comp: CarritoHistoricoUpdateComponent;
    let fixture: ComponentFixture<CarritoHistoricoUpdateComponent>;
    let service: CarritoHistoricoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [CarritoHistoricoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CarritoHistoricoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CarritoHistoricoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarritoHistoricoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CarritoHistorico(123);
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
        const entity = new CarritoHistorico();
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
