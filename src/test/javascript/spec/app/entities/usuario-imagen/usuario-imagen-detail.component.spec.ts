/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { UsuarioImagenDetailComponent } from 'app/entities/usuario-imagen/usuario-imagen-detail.component';
import { UsuarioImagen } from 'app/shared/model/usuario-imagen.model';

describe('Component Tests', () => {
  describe('UsuarioImagen Management Detail Component', () => {
    let comp: UsuarioImagenDetailComponent;
    let fixture: ComponentFixture<UsuarioImagenDetailComponent>;
    const route = ({ data: of({ usuarioImagen: new UsuarioImagen(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [UsuarioImagenDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UsuarioImagenDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UsuarioImagenDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.usuarioImagen).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
