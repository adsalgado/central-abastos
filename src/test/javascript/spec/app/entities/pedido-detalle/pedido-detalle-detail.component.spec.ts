/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { PedidoDetalleDetailComponent } from 'app/entities/pedido-detalle/pedido-detalle-detail.component';
import { PedidoDetalle } from 'app/shared/model/pedido-detalle.model';

describe('Component Tests', () => {
  describe('PedidoDetalle Management Detail Component', () => {
    let comp: PedidoDetalleDetailComponent;
    let fixture: ComponentFixture<PedidoDetalleDetailComponent>;
    const route = ({ data: of({ pedidoDetalle: new PedidoDetalle(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [PedidoDetalleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PedidoDetalleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PedidoDetalleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pedidoDetalle).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
