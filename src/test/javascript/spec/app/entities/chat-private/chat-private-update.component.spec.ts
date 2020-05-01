/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AbastosTestModule } from '../../../test.module';
import { ChatPrivateUpdateComponent } from 'app/entities/chat-private/chat-private-update.component';
import { ChatPrivateService } from 'app/entities/chat-private/chat-private.service';
import { ChatPrivate } from 'app/shared/model/chat-private.model';

describe('Component Tests', () => {
  describe('ChatPrivate Management Update Component', () => {
    let comp: ChatPrivateUpdateComponent;
    let fixture: ComponentFixture<ChatPrivateUpdateComponent>;
    let service: ChatPrivateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AbastosTestModule],
        declarations: [ChatPrivateUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ChatPrivateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChatPrivateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChatPrivateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ChatPrivate(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ChatPrivate();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
