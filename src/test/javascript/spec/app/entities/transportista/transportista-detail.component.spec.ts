/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { TransportistaDetailComponent } from 'app/entities/transportista/transportista-detail.component';
import { Transportista } from 'app/shared/model/transportista.model';

describe('Component Tests', () => {
  describe('Transportista Management Detail Component', () => {
    let comp: TransportistaDetailComponent;
    let fixture: ComponentFixture<TransportistaDetailComponent>;
    const route = ({ data: of({ transportista: new Transportista(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [TransportistaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TransportistaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransportistaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transportista).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
