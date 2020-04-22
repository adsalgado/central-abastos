/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { PagosDetailComponent } from 'app/entities/pagos/pagos-detail.component';
import { Pagos } from 'app/shared/model/pagos.model';

describe('Component Tests', () => {
  describe('Pagos Management Detail Component', () => {
    let comp: PagosDetailComponent;
    let fixture: ComponentFixture<PagosDetailComponent>;
    const route = ({ data: of({ pagos: new Pagos(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [PagosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PagosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PagosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pagos).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
