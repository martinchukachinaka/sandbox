/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { ApiConsumerProfileUpdateComponent } from 'app/entities/api-consumer-profile/api-consumer-profile-update.component';
import { ApiConsumerProfileService } from 'app/entities/api-consumer-profile/api-consumer-profile.service';
import { ApiConsumerProfile } from 'app/shared/model/api-consumer-profile.model';

describe('Component Tests', () => {
    describe('ApiConsumerProfile Management Update Component', () => {
        let comp: ApiConsumerProfileUpdateComponent;
        let fixture: ComponentFixture<ApiConsumerProfileUpdateComponent>;
        let service: ApiConsumerProfileService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [ApiConsumerProfileUpdateComponent]
            })
                .overrideTemplate(ApiConsumerProfileUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApiConsumerProfileUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApiConsumerProfileService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ApiConsumerProfile(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.apiConsumerProfile = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ApiConsumerProfile();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.apiConsumerProfile = entity;
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
