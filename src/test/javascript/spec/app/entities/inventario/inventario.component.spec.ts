/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { InventarioComponent } from 'app/entities/inventario/inventario.component';
import { InventarioService } from 'app/entities/inventario/inventario.service';
import { Inventario } from 'app/shared/model/inventario.model';

describe('Component Tests', () => {
  describe('Inventario Management Component', () => {
    let comp: InventarioComponent;
    let fixture: ComponentFixture<InventarioComponent>;
    let service: InventarioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [InventarioComponent],
        providers: []
      })
        .overrideTemplate(InventarioComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InventarioComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventarioService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Inventario(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.inventarios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
