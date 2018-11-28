/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ApicockpitTestModule } from '../../../test.module';
import { ApiSvcProcConfigComponent } from 'app/entities/api-svc-proc-config/api-svc-proc-config.component';
import { ApiSvcProcConfigService } from 'app/entities/api-svc-proc-config/api-svc-proc-config.service';
import { ApiSvcProcConfig } from 'app/shared/model/api-svc-proc-config.model';

describe('Component Tests', () => {
    describe('ApiSvcProcConfig Management Component', () => {
        let comp: ApiSvcProcConfigComponent;
        let fixture: ComponentFixture<ApiSvcProcConfigComponent>;
        let service: ApiSvcProcConfigService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [ApiSvcProcConfigComponent],
                providers: []
            })
                .overrideTemplate(ApiSvcProcConfigComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApiSvcProcConfigComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApiSvcProcConfigService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ApiSvcProcConfig(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.apiSvcProcConfigs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
