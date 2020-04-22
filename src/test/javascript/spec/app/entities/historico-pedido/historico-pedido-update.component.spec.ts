/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { HistoricoPedidoUpdateComponent } from 'app/entities/historico-pedido/historico-pedido-update.component';
import { HistoricoPedidoService } from 'app/entities/historico-pedido/historico-pedido.service';
import { HistoricoPedido } from 'app/shared/model/historico-pedido.model';

describe('Component Tests', () => {
  describe('HistoricoPedido Management Update Component', () => {
    let comp: HistoricoPedidoUpdateComponent;
    let fixture: ComponentFixture<HistoricoPedidoUpdateComponent>;
    let service: HistoricoPedidoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [HistoricoPedidoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(HistoricoPedidoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HistoricoPedidoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HistoricoPedidoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new HistoricoPedido(123);
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
        const entity = new HistoricoPedido();
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
