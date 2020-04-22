/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { TipoOfertaDetailComponent } from 'app/entities/tipo-oferta/tipo-oferta-detail.component';
import { TipoOferta } from 'app/shared/model/tipo-oferta.model';

describe('Component Tests', () => {
  describe('TipoOferta Management Detail Component', () => {
    let comp: TipoOfertaDetailComponent;
    let fixture: ComponentFixture<TipoOfertaDetailComponent>;
    const route = ({ data: of({ tipoOferta: new TipoOferta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [TipoOfertaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoOfertaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoOfertaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoOferta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
