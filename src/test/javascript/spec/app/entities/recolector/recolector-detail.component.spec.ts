/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { RecolectorDetailComponent } from 'app/entities/recolector/recolector-detail.component';
import { Recolector } from 'app/shared/model/recolector.model';

describe('Component Tests', () => {
  describe('Recolector Management Detail Component', () => {
    let comp: RecolectorDetailComponent;
    let fixture: ComponentFixture<RecolectorDetailComponent>;
    const route = ({ data: of({ recolector: new Recolector(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [RecolectorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RecolectorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecolectorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.recolector).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
