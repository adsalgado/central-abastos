/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { RecolectorTarifaComponent } from 'app/entities/recolector-tarifa/recolector-tarifa.component';
import { RecolectorTarifaService } from 'app/entities/recolector-tarifa/recolector-tarifa.service';
import { RecolectorTarifa } from 'app/shared/model/recolector-tarifa.model';

describe('Component Tests', () => {
  describe('RecolectorTarifa Management Component', () => {
    let comp: RecolectorTarifaComponent;
    let fixture: ComponentFixture<RecolectorTarifaComponent>;
    let service: RecolectorTarifaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [RecolectorTarifaComponent],
        providers: []
      })
        .overrideTemplate(RecolectorTarifaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecolectorTarifaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecolectorTarifaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RecolectorTarifa(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.recolectorTarifas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
