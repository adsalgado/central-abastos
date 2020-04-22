/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { UsuarioImagenComponent } from 'app/entities/usuario-imagen/usuario-imagen.component';
import { UsuarioImagenService } from 'app/entities/usuario-imagen/usuario-imagen.service';
import { UsuarioImagen } from 'app/shared/model/usuario-imagen.model';

describe('Component Tests', () => {
  describe('UsuarioImagen Management Component', () => {
    let comp: UsuarioImagenComponent;
    let fixture: ComponentFixture<UsuarioImagenComponent>;
    let service: UsuarioImagenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [UsuarioImagenComponent],
        providers: []
      })
        .overrideTemplate(UsuarioImagenComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UsuarioImagenComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UsuarioImagenService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UsuarioImagen(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.usuarioImagens[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
