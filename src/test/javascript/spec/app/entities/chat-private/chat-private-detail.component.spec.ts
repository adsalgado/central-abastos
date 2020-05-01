/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { ChatPrivateDetailComponent } from 'app/entities/chat-private/chat-private-detail.component';
import { ChatPrivate } from 'app/shared/model/chat-private.model';

describe('Component Tests', () => {
  describe('ChatPrivate Management Detail Component', () => {
    let comp: ChatPrivateDetailComponent;
    let fixture: ComponentFixture<ChatPrivateDetailComponent>;
    const route = ({ data: of({ chatPrivate: new ChatPrivate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [ChatPrivateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ChatPrivateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChatPrivateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.chatPrivate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
