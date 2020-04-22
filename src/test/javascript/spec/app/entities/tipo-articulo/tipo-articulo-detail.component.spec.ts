/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { TipoArticuloDetailComponent } from 'app/entities/tipo-articulo/tipo-articulo-detail.component';
import { TipoArticulo } from 'app/shared/model/tipo-articulo.model';

describe('Component Tests', () => {
  describe('TipoArticulo Management Detail Component', () => {
    let comp: TipoArticuloDetailComponent;
    let fixture: ComponentFixture<TipoArticuloDetailComponent>;
    const route = ({ data: of({ tipoArticulo: new TipoArticulo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [TipoArticuloDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoArticuloDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoArticuloDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoArticulo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
