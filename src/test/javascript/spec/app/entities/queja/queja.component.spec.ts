/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { QuejaComponent } from 'app/entities/queja/queja.component';
import { QuejaService } from 'app/entities/queja/queja.service';
import { Queja } from 'app/shared/model/queja.model';

describe('Component Tests', () => {
  describe('Queja Management Component', () => {
    let comp: QuejaComponent;
    let fixture: ComponentFixture<QuejaComponent>;
    let service: QuejaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [QuejaComponent],
        providers: []
      })
        .overrideTemplate(QuejaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuejaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuejaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Queja(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.quejas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
