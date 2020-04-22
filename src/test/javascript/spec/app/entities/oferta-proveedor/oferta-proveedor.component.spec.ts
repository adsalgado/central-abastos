/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { OfertaProveedorComponent } from 'app/entities/oferta-proveedor/oferta-proveedor.component';
import { OfertaProveedorService } from 'app/entities/oferta-proveedor/oferta-proveedor.service';
import { OfertaProveedor } from 'app/shared/model/oferta-proveedor.model';

describe('Component Tests', () => {
  describe('OfertaProveedor Management Component', () => {
    let comp: OfertaProveedorComponent;
    let fixture: ComponentFixture<OfertaProveedorComponent>;
    let service: OfertaProveedorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [OfertaProveedorComponent],
        providers: []
      })
        .overrideTemplate(OfertaProveedorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OfertaProveedorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OfertaProveedorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OfertaProveedor(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.ofertaProveedors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
