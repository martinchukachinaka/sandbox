/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { ApiSvcProcConfigUpdateComponent } from 'app/entities/api-svc-proc-config/api-svc-proc-config-update.component';
import { ApiSvcProcConfigService } from 'app/entities/api-svc-proc-config/api-svc-proc-config.service';
import { ApiSvcProcConfig } from 'app/shared/model/api-svc-proc-config.model';

describe('Component Tests', () => {
    describe('ApiSvcProcConfig Management Update Component', () => {
        let comp: ApiSvcProcConfigUpdateComponent;
        let fixture: ComponentFixture<ApiSvcProcConfigUpdateComponent>;
        let service: ApiSvcProcConfigService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [ApiSvcProcConfigUpdateComponent]
            })
                .overrideTemplate(ApiSvcProcConfigUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApiSvcProcConfigUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApiSvcProcConfigService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ApiSvcProcConfig(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.apiSvcProcConfig = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ApiSvcProcConfig();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.apiSvcProcConfig = entity;
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
