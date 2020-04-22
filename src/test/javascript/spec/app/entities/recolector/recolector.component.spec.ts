/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { RecolectorComponent } from 'app/entities/recolector/recolector.component';
import { RecolectorService } from 'app/entities/recolector/recolector.service';
import { Recolector } from 'app/shared/model/recolector.model';

describe('Component Tests', () => {
  describe('Recolector Management Component', () => {
    let comp: RecolectorComponent;
    let fixture: ComponentFixture<RecolectorComponent>;
    let service: RecolectorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [RecolectorComponent],
        providers: []
      })
        .overrideTemplate(RecolectorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecolectorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecolectorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Recolector(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.recolectors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
