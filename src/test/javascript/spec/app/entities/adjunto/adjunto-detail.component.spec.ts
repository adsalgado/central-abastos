/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { AdjuntoDetailComponent } from 'app/entities/adjunto/adjunto-detail.component';
import { Adjunto } from 'app/shared/model/adjunto.model';

describe('Component Tests', () => {
  describe('Adjunto Management Detail Component', () => {
    let comp: AdjuntoDetailComponent;
    let fixture: ComponentFixture<AdjuntoDetailComponent>;
    const route = ({ data: of({ adjunto: new Adjunto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [AdjuntoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AdjuntoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AdjuntoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.adjunto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
