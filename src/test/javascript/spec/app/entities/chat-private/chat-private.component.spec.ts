/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AbastosTestModule } from '../../../test.module';
import { ChatPrivateComponent } from 'app/entities/chat-private/chat-private.component';
import { ChatPrivateService } from 'app/entities/chat-private/chat-private.service';
import { ChatPrivate } from 'app/shared/model/chat-private.model';

describe('Component Tests', () => {
  describe('ChatPrivate Management Component', () => {
    let comp: ChatPrivateComponent;
    let fixture: ComponentFixture<ChatPrivateComponent>;
    let service: ChatPrivateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [ChatPrivateComponent],
        providers: []
      })
        .overrideTemplate(ChatPrivateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChatPrivateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChatPrivateService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ChatPrivate(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.chatPrivates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
