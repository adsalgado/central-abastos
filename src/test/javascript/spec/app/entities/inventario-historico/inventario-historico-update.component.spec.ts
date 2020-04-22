/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { InventarioHistoricoUpdateComponent } from 'app/entities/inventario-historico/inventario-historico-update.component';
import { InventarioHistoricoService } from 'app/entities/inventario-historico/inventario-historico.service';
import { InventarioHistorico } from 'app/shared/model/inventario-historico.model';

describe('Component Tests', () => {
  describe('InventarioHistorico Management Update Component', () => {
    let comp: InventarioHistoricoUpdateComponent;
    let fixture: ComponentFixture<InventarioHistoricoUpdateComponent>;
    let service: InventarioHistoricoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [InventarioHistoricoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InventarioHistoricoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InventarioHistoricoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventarioHistoricoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InventarioHistorico(123);
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
        const entity = new InventarioHistorico();
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
