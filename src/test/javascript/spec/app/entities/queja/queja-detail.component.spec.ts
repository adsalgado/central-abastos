/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { QuejaDetailComponent } from 'app/entities/queja/queja-detail.component';
import { Queja } from 'app/shared/model/queja.model';

describe('Component Tests', () => {
  describe('Queja Management Detail Component', () => {
    let comp: QuejaDetailComponent;
    let fixture: ComponentFixture<QuejaDetailComponent>;
    const route = ({ data: of({ queja: new Queja(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [QuejaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(QuejaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuejaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.queja).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
