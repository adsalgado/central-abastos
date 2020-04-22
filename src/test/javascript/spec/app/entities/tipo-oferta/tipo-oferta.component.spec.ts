/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { TipoOfertaComponent } from 'app/entities/tipo-oferta/tipo-oferta.component';
import { TipoOfertaService } from 'app/entities/tipo-oferta/tipo-oferta.service';
import { TipoOferta } from 'app/shared/model/tipo-oferta.model';

describe('Component Tests', () => {
  describe('TipoOferta Management Component', () => {
    let comp: TipoOfertaComponent;
    let fixture: ComponentFixture<TipoOfertaComponent>;
    let service: TipoOfertaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [TipoOfertaComponent],
        providers: []
      })
        .overrideTemplate(TipoOfertaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoOfertaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoOfertaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoOferta(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoOfertas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
