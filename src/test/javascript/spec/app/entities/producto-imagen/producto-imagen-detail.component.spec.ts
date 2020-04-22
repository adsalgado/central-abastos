/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { ProductoImagenDetailComponent } from 'app/entities/producto-imagen/producto-imagen-detail.component';
import { ProductoImagen } from 'app/shared/model/producto-imagen.model';

describe('Component Tests', () => {
  describe('ProductoImagen Management Detail Component', () => {
    let comp: ProductoImagenDetailComponent;
    let fixture: ComponentFixture<ProductoImagenDetailComponent>;
    const route = ({ data: of({ productoImagen: new ProductoImagen(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [ProductoImagenDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductoImagenDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductoImagenDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productoImagen).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
