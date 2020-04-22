/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { HistoricoPedidoDetailComponent } from 'app/entities/historico-pedido/historico-pedido-detail.component';
import { HistoricoPedido } from 'app/shared/model/historico-pedido.model';

describe('Component Tests', () => {
  describe('HistoricoPedido Management Detail Component', () => {
    let comp: HistoricoPedidoDetailComponent;
    let fixture: ComponentFixture<HistoricoPedidoDetailComponent>;
    const route = ({ data: of({ historicoPedido: new HistoricoPedido(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [HistoricoPedidoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(HistoricoPedidoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HistoricoPedidoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.historicoPedido).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
