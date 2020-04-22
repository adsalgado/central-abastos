/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { TransportistaTarifaComponent } from 'app/entities/transportista-tarifa/transportista-tarifa.component';
import { TransportistaTarifaService } from 'app/entities/transportista-tarifa/transportista-tarifa.service';
import { TransportistaTarifa } from 'app/shared/model/transportista-tarifa.model';

describe('Component Tests', () => {
  describe('TransportistaTarifa Management Component', () => {
    let comp: TransportistaTarifaComponent;
    let fixture: ComponentFixture<TransportistaTarifaComponent>;
    let service: TransportistaTarifaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [TransportistaTarifaComponent],
        providers: []
      })
        .overrideTemplate(TransportistaTarifaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransportistaTarifaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransportistaTarifaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TransportistaTarifa(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transportistaTarifas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
