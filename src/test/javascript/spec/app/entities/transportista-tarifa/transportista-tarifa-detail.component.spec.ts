/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { TransportistaTarifaDetailComponent } from 'app/entities/transportista-tarifa/transportista-tarifa-detail.component';
import { TransportistaTarifa } from 'app/shared/model/transportista-tarifa.model';

describe('Component Tests', () => {
  describe('TransportistaTarifa Management Detail Component', () => {
    let comp: TransportistaTarifaDetailComponent;
    let fixture: ComponentFixture<TransportistaTarifaDetailComponent>;
    const route = ({ data: of({ transportistaTarifa: new TransportistaTarifa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [TransportistaTarifaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TransportistaTarifaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransportistaTarifaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transportistaTarifa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
