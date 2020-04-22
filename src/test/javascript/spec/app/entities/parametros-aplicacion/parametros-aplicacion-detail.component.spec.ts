/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { ParametrosAplicacionDetailComponent } from 'app/entities/parametros-aplicacion/parametros-aplicacion-detail.component';
import { ParametrosAplicacion } from 'app/shared/model/parametros-aplicacion.model';

describe('Component Tests', () => {
  describe('ParametrosAplicacion Management Detail Component', () => {
    let comp: ParametrosAplicacionDetailComponent;
    let fixture: ComponentFixture<ParametrosAplicacionDetailComponent>;
    const route = ({ data: of({ parametrosAplicacion: new ParametrosAplicacion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [ParametrosAplicacionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ParametrosAplicacionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParametrosAplicacionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.parametrosAplicacion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
