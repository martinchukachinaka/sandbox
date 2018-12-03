/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { ApiProjectUpdateComponent } from 'app/entities/api-project/api-project-update.component';
import { ApiProjectService } from 'app/entities/api-project/api-project.service';
import { ApiProject } from 'app/shared/model/api-project.model';

describe('Component Tests', () => {
    describe('ApiProject Management Update Component', () => {
        let comp: ApiProjectUpdateComponent;
        let fixture: ComponentFixture<ApiProjectUpdateComponent>;
        let service: ApiProjectService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [ApiProjectUpdateComponent]
            })
                .overrideTemplate(ApiProjectUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApiProjectUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApiProjectService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ApiProject(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.apiProject = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ApiProject();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.apiProject = entity;
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
