/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { RecolectorTarifaDetailComponent } from 'app/entities/recolector-tarifa/recolector-tarifa-detail.component';
import { RecolectorTarifa } from 'app/shared/model/recolector-tarifa.model';

describe('Component Tests', () => {
  describe('RecolectorTarifa Management Detail Component', () => {
    let comp: RecolectorTarifaDetailComponent;
    let fixture: ComponentFixture<RecolectorTarifaDetailComponent>;
    const route = ({ data: of({ recolectorTarifa: new RecolectorTarifa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [RecolectorTarifaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RecolectorTarifaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecolectorTarifaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.recolectorTarifa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
