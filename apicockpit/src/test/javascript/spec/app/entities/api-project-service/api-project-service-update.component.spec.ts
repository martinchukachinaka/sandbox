/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { ApiProjectServiceUpdateComponent } from 'app/entities/api-project-service/api-project-service-update.component';
import { ApiProjectServiceService } from 'app/entities/api-project-service/api-project-service.service';
import { ApiProjectService } from 'app/shared/model/api-project-service.model';

describe('Component Tests', () => {
    describe('ApiProjectService Management Update Component', () => {
        let comp: ApiProjectServiceUpdateComponent;
        let fixture: ComponentFixture<ApiProjectServiceUpdateComponent>;
        let service: ApiProjectServiceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [ApiProjectServiceUpdateComponent]
            })
                .overrideTemplate(ApiProjectServiceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApiProjectServiceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApiProjectServiceService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ApiProjectService(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.apiProjectService = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ApiProjectService();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.apiProjectService = entity;
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
