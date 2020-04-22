/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { CarritoHistoricoComponent } from 'app/entities/carrito-historico/carrito-historico.component';
import { CarritoHistoricoService } from 'app/entities/carrito-historico/carrito-historico.service';
import { CarritoHistorico } from 'app/shared/model/carrito-historico.model';

describe('Component Tests', () => {
  describe('CarritoHistorico Management Component', () => {
    let comp: CarritoHistoricoComponent;
    let fixture: ComponentFixture<CarritoHistoricoComponent>;
    let service: CarritoHistoricoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [CarritoHistoricoComponent],
        providers: []
      })
        .overrideTemplate(CarritoHistoricoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CarritoHistoricoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarritoHistoricoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CarritoHistorico(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.carritoHistoricos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
