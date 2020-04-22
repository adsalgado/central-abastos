/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { TipoArticuloComponent } from 'app/entities/tipo-articulo/tipo-articulo.component';
import { TipoArticuloService } from 'app/entities/tipo-articulo/tipo-articulo.service';
import { TipoArticulo } from 'app/shared/model/tipo-articulo.model';

describe('Component Tests', () => {
  describe('TipoArticulo Management Component', () => {
    let comp: TipoArticuloComponent;
    let fixture: ComponentFixture<TipoArticuloComponent>;
    let service: TipoArticuloService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [TipoArticuloComponent],
        providers: []
      })
        .overrideTemplate(TipoArticuloComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoArticuloComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoArticuloService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoArticulo(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoArticulos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
