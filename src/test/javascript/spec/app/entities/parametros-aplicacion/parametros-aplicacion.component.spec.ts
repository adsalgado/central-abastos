/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { ParametrosAplicacionComponent } from 'app/entities/parametros-aplicacion/parametros-aplicacion.component';
import { ParametrosAplicacionService } from 'app/entities/parametros-aplicacion/parametros-aplicacion.service';
import { ParametrosAplicacion } from 'app/shared/model/parametros-aplicacion.model';

describe('Component Tests', () => {
  describe('ParametrosAplicacion Management Component', () => {
    let comp: ParametrosAplicacionComponent;
    let fixture: ComponentFixture<ParametrosAplicacionComponent>;
    let service: ParametrosAplicacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [ParametrosAplicacionComponent],
        providers: []
      })
        .overrideTemplate(ParametrosAplicacionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParametrosAplicacionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParametrosAplicacionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ParametrosAplicacion(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.parametrosAplicacions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
