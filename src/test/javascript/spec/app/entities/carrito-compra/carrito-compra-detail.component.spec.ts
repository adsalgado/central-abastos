/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { CarritoCompraDetailComponent } from 'app/entities/carrito-compra/carrito-compra-detail.component';
import { CarritoCompra } from 'app/shared/model/carrito-compra.model';

describe('Component Tests', () => {
  describe('CarritoCompra Management Detail Component', () => {
    let comp: CarritoCompraDetailComponent;
    let fixture: ComponentFixture<CarritoCompraDetailComponent>;
    const route = ({ data: of({ carritoCompra: new CarritoCompra(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [CarritoCompraDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CarritoCompraDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CarritoCompraDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.carritoCompra).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
