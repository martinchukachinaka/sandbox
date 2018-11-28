/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { ApiPublisherProfileUpdateComponent } from 'app/entities/api-publisher-profile/api-publisher-profile-update.component';
import { ApiPublisherProfileService } from 'app/entities/api-publisher-profile/api-publisher-profile.service';
import { ApiPublisherProfile } from 'app/shared/model/api-publisher-profile.model';

describe('Component Tests', () => {
    describe('ApiPublisherProfile Management Update Component', () => {
        let comp: ApiPublisherProfileUpdateComponent;
        let fixture: ComponentFixture<ApiPublisherProfileUpdateComponent>;
        let service: ApiPublisherProfileService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [ApiPublisherProfileUpdateComponent]
            })
                .overrideTemplate(ApiPublisherProfileUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApiPublisherProfileUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApiPublisherProfileService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ApiPublisherProfile(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.apiPublisherProfile = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ApiPublisherProfile();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.apiPublisherProfile = entity;
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
