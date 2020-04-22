/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { CarritoCompraComponent } from 'app/entities/carrito-compra/carrito-compra.component';
import { CarritoCompraService } from 'app/entities/carrito-compra/carrito-compra.service';
import { CarritoCompra } from 'app/shared/model/carrito-compra.model';

describe('Component Tests', () => {
  describe('CarritoCompra Management Component', () => {
    let comp: CarritoCompraComponent;
    let fixture: ComponentFixture<CarritoCompraComponent>;
    let service: CarritoCompraService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [CarritoCompraComponent],
        providers: []
      })
        .overrideTemplate(CarritoCompraComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CarritoCompraComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarritoCompraService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CarritoCompra(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.carritoCompras[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
