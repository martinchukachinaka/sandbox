/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { ApiProjectAuthConfigUpdateComponent } from 'app/entities/api-project-auth-config/api-project-auth-config-update.component';
import { ApiProjectAuthConfigService } from 'app/entities/api-project-auth-config/api-project-auth-config.service';
import { ApiProjectAuthConfig } from 'app/shared/model/api-project-auth-config.model';

describe('Component Tests', () => {
    describe('ApiProjectAuthConfig Management Update Component', () => {
        let comp: ApiProjectAuthConfigUpdateComponent;
        let fixture: ComponentFixture<ApiProjectAuthConfigUpdateComponent>;
        let service: ApiProjectAuthConfigService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [ApiProjectAuthConfigUpdateComponent]
            })
                .overrideTemplate(ApiProjectAuthConfigUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApiProjectAuthConfigUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApiProjectAuthConfigService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ApiProjectAuthConfig(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.apiProjectAuthConfig = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ApiProjectAuthConfig();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.apiProjectAuthConfig = entity;
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
