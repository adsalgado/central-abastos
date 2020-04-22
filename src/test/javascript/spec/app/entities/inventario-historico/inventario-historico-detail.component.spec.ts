/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { InventarioHistoricoDetailComponent } from 'app/entities/inventario-historico/inventario-historico-detail.component';
import { InventarioHistorico } from 'app/shared/model/inventario-historico.model';

describe('Component Tests', () => {
  describe('InventarioHistorico Management Detail Component', () => {
    let comp: InventarioHistoricoDetailComponent;
    let fixture: ComponentFixture<InventarioHistoricoDetailComponent>;
    const route = ({ data: of({ inventarioHistorico: new InventarioHistorico(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [InventarioHistoricoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InventarioHistoricoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InventarioHistoricoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.inventarioHistorico).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
