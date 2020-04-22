/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { PedidoDetalleUpdateComponent } from 'app/entities/pedido-detalle/pedido-detalle-update.component';
import { PedidoDetalleService } from 'app/entities/pedido-detalle/pedido-detalle.service';
import { PedidoDetalle } from 'app/shared/model/pedido-detalle.model';

describe('Component Tests', () => {
  describe('PedidoDetalle Management Update Component', () => {
    let comp: PedidoDetalleUpdateComponent;
    let fixture: ComponentFixture<PedidoDetalleUpdateComponent>;
    let service: PedidoDetalleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [PedidoDetalleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PedidoDetalleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PedidoDetalleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PedidoDetalleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PedidoDetalle(123);
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
        const entity = new PedidoDetalle();
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
