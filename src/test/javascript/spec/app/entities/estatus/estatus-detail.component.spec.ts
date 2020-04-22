/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { EstatusDetailComponent } from 'app/entities/estatus/estatus-detail.component';
import { Estatus } from 'app/shared/model/estatus.model';

describe('Component Tests', () => {
  describe('Estatus Management Detail Component', () => {
    let comp: EstatusDetailComponent;
    let fixture: ComponentFixture<EstatusDetailComponent>;
    const route = ({ data: of({ estatus: new Estatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [EstatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EstatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
