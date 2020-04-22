/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { AdjuntoComponent } from 'app/entities/adjunto/adjunto.component';
import { AdjuntoService } from 'app/entities/adjunto/adjunto.service';
import { Adjunto } from 'app/shared/model/adjunto.model';

describe('Component Tests', () => {
  describe('Adjunto Management Component', () => {
    let comp: AdjuntoComponent;
    let fixture: ComponentFixture<AdjuntoComponent>;
    let service: AdjuntoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [AdjuntoComponent],
        providers: []
      })
        .overrideTemplate(AdjuntoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdjuntoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdjuntoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Adjunto(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.adjuntos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
