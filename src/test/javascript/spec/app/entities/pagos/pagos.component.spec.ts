/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { PagosComponent } from 'app/entities/pagos/pagos.component';
import { PagosService } from 'app/entities/pagos/pagos.service';
import { Pagos } from 'app/shared/model/pagos.model';

describe('Component Tests', () => {
  describe('Pagos Management Component', () => {
    let comp: PagosComponent;
    let fixture: ComponentFixture<PagosComponent>;
    let service: PagosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [PagosComponent],
        providers: []
      })
        .overrideTemplate(PagosComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PagosComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PagosService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Pagos(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pagos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
