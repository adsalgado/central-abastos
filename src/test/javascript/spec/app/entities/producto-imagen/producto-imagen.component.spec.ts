/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { ProductoImagenComponent } from 'app/entities/producto-imagen/producto-imagen.component';
import { ProductoImagenService } from 'app/entities/producto-imagen/producto-imagen.service';
import { ProductoImagen } from 'app/shared/model/producto-imagen.model';

describe('Component Tests', () => {
  describe('ProductoImagen Management Component', () => {
    let comp: ProductoImagenComponent;
    let fixture: ComponentFixture<ProductoImagenComponent>;
    let service: ProductoImagenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [ProductoImagenComponent],
        providers: []
      })
        .overrideTemplate(ProductoImagenComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductoImagenComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductoImagenService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductoImagen(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productoImagens[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
