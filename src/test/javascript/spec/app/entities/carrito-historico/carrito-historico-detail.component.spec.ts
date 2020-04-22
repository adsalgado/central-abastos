/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { CarritoHistoricoDetailComponent } from 'app/entities/carrito-historico/carrito-historico-detail.component';
import { CarritoHistorico } from 'app/shared/model/carrito-historico.model';

describe('Component Tests', () => {
  describe('CarritoHistorico Management Detail Component', () => {
    let comp: CarritoHistoricoDetailComponent;
    let fixture: ComponentFixture<CarritoHistoricoDetailComponent>;
    const route = ({ data: of({ carritoHistorico: new CarritoHistorico(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [CarritoHistoricoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CarritoHistoricoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CarritoHistoricoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.carritoHistorico).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
