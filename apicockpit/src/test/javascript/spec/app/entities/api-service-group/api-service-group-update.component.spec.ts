/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { ApiServiceGroupUpdateComponent } from 'app/entities/api-service-group/api-service-group-update.component';
import { ApiServiceGroupService } from 'app/entities/api-service-group/api-service-group.service';
import { ApiServiceGroup } from 'app/shared/model/api-service-group.model';

describe('Component Tests', () => {
    describe('ApiServiceGroup Management Update Component', () => {
        let comp: ApiServiceGroupUpdateComponent;
        let fixture: ComponentFixture<ApiServiceGroupUpdateComponent>;
        let service: ApiServiceGroupService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [ApiServiceGroupUpdateComponent]
            })
                .overrideTemplate(ApiServiceGroupUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApiServiceGroupUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApiServiceGroupService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ApiServiceGroup(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.apiServiceGroup = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ApiServiceGroup();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.apiServiceGroup = entity;
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
