/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { PedidoDetalleComponent } from 'app/entities/pedido-detalle/pedido-detalle.component';
import { PedidoDetalleService } from 'app/entities/pedido-detalle/pedido-detalle.service';
import { PedidoDetalle } from 'app/shared/model/pedido-detalle.model';

describe('Component Tests', () => {
  describe('PedidoDetalle Management Component', () => {
    let comp: PedidoDetalleComponent;
    let fixture: ComponentFixture<PedidoDetalleComponent>;
    let service: PedidoDetalleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [PedidoDetalleComponent],
        providers: []
      })
        .overrideTemplate(PedidoDetalleComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PedidoDetalleComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PedidoDetalleService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PedidoDetalle(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pedidoDetalles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
