/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { SeccionComponent } from 'app/entities/seccion/seccion.component';
import { SeccionService } from 'app/entities/seccion/seccion.service';
import { Seccion } from 'app/shared/model/seccion.model';

describe('Component Tests', () => {
  describe('Seccion Management Component', () => {
    let comp: SeccionComponent;
    let fixture: ComponentFixture<SeccionComponent>;
    let service: SeccionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [SeccionComponent],
        providers: []
      })
        .overrideTemplate(SeccionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SeccionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SeccionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Seccion(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.seccions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
