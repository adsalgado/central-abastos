/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { HistoricoPedidoComponent } from 'app/entities/historico-pedido/historico-pedido.component';
import { HistoricoPedidoService } from 'app/entities/historico-pedido/historico-pedido.service';
import { HistoricoPedido } from 'app/shared/model/historico-pedido.model';

describe('Component Tests', () => {
  describe('HistoricoPedido Management Component', () => {
    let comp: HistoricoPedidoComponent;
    let fixture: ComponentFixture<HistoricoPedidoComponent>;
    let service: HistoricoPedidoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [HistoricoPedidoComponent],
        providers: []
      })
        .overrideTemplate(HistoricoPedidoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HistoricoPedidoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HistoricoPedidoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new HistoricoPedido(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.historicoPedidos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
