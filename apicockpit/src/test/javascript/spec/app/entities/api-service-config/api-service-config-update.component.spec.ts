/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { ApiServiceConfigUpdateComponent } from 'app/entities/api-service-config/api-service-config-update.component';
import { ApiServiceConfigService } from 'app/entities/api-service-config/api-service-config.service';
import { ApiServiceConfig } from 'app/shared/model/api-service-config.model';

describe('Component Tests', () => {
    describe('ApiServiceConfig Management Update Component', () => {
        let comp: ApiServiceConfigUpdateComponent;
        let fixture: ComponentFixture<ApiServiceConfigUpdateComponent>;
        let service: ApiServiceConfigService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [ApiServiceConfigUpdateComponent]
            })
                .overrideTemplate(ApiServiceConfigUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApiServiceConfigUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApiServiceConfigService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ApiServiceConfig(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.apiServiceConfig = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ApiServiceConfig();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.apiServiceConfig = entity;
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
