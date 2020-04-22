/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { CarritoHistoricoDetalleComponent } from 'app/entities/carrito-historico-detalle/carrito-historico-detalle.component';
import { CarritoHistoricoDetalleService } from 'app/entities/carrito-historico-detalle/carrito-historico-detalle.service';
import { CarritoHistoricoDetalle } from 'app/shared/model/carrito-historico-detalle.model';

describe('Component Tests', () => {
  describe('CarritoHistoricoDetalle Management Component', () => {
    let comp: CarritoHistoricoDetalleComponent;
    let fixture: ComponentFixture<CarritoHistoricoDetalleComponent>;
    let service: CarritoHistoricoDetalleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [CarritoHistoricoDetalleComponent],
        providers: []
      })
        .overrideTemplate(CarritoHistoricoDetalleComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CarritoHistoricoDetalleComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarritoHistoricoDetalleService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CarritoHistoricoDetalle(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.carritoHistoricoDetalles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
