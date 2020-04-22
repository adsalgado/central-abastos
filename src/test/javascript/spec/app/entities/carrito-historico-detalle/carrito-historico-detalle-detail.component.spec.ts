/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { CarritoHistoricoDetalleDetailComponent } from 'app/entities/carrito-historico-detalle/carrito-historico-detalle-detail.component';
import { CarritoHistoricoDetalle } from 'app/shared/model/carrito-historico-detalle.model';

describe('Component Tests', () => {
  describe('CarritoHistoricoDetalle Management Detail Component', () => {
    let comp: CarritoHistoricoDetalleDetailComponent;
    let fixture: ComponentFixture<CarritoHistoricoDetalleDetailComponent>;
    const route = ({ data: of({ carritoHistoricoDetalle: new CarritoHistoricoDetalle(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [CarritoHistoricoDetalleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CarritoHistoricoDetalleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CarritoHistoricoDetalleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.carritoHistoricoDetalle).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
