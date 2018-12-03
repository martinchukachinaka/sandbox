/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { PlatformUserUpdateComponent } from 'app/entities/platform-user/platform-user-update.component';
import { PlatformUserService } from 'app/entities/platform-user/platform-user.service';
import { PlatformUser } from 'app/shared/model/platform-user.model';

describe('Component Tests', () => {
    describe('PlatformUser Management Update Component', () => {
        let comp: PlatformUserUpdateComponent;
        let fixture: ComponentFixture<PlatformUserUpdateComponent>;
        let service: PlatformUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [PlatformUserUpdateComponent]
            })
                .overrideTemplate(PlatformUserUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PlatformUserUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlatformUserService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new PlatformUser(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.platformUser = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new PlatformUser();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.platformUser = entity;
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
