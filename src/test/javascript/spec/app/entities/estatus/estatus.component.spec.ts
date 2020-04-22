/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { EstatusComponent } from 'app/entities/estatus/estatus.component';
import { EstatusService } from 'app/entities/estatus/estatus.service';
import { Estatus } from 'app/shared/model/estatus.model';

describe('Component Tests', () => {
  describe('Estatus Management Component', () => {
    let comp: EstatusComponent;
    let fixture: ComponentFixture<EstatusComponent>;
    let service: EstatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [EstatusComponent],
        providers: []
      })
        .overrideTemplate(EstatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstatusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstatusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Estatus(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.estatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
