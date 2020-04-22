/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { TransportistaComponent } from 'app/entities/transportista/transportista.component';
import { TransportistaService } from 'app/entities/transportista/transportista.service';
import { Transportista } from 'app/shared/model/transportista.model';

describe('Component Tests', () => {
  describe('Transportista Management Component', () => {
    let comp: TransportistaComponent;
    let fixture: ComponentFixture<TransportistaComponent>;
    let service: TransportistaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [TransportistaComponent],
        providers: []
      })
        .overrideTemplate(TransportistaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransportistaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransportistaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Transportista(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transportistas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
