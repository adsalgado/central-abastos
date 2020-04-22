/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { OfertaProveedorDetailComponent } from 'app/entities/oferta-proveedor/oferta-proveedor-detail.component';
import { OfertaProveedor } from 'app/shared/model/oferta-proveedor.model';

describe('Component Tests', () => {
  describe('OfertaProveedor Management Detail Component', () => {
    let comp: OfertaProveedorDetailComponent;
    let fixture: ComponentFixture<OfertaProveedorDetailComponent>;
    const route = ({ data: of({ ofertaProveedor: new OfertaProveedor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [OfertaProveedorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OfertaProveedorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OfertaProveedorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ofertaProveedor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
