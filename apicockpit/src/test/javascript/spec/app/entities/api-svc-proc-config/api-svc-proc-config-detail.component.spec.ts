/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { ApiSvcProcConfigDetailComponent } from 'app/entities/api-svc-proc-config/api-svc-proc-config-detail.component';
import { ApiSvcProcConfig } from 'app/shared/model/api-svc-proc-config.model';

describe('Component Tests', () => {
    describe('ApiSvcProcConfig Management Detail Component', () => {
        let comp: ApiSvcProcConfigDetailComponent;
        let fixture: ComponentFixture<ApiSvcProcConfigDetailComponent>;
        const route = ({ data: of({ apiSvcProcConfig: new ApiSvcProcConfig(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [ApiSvcProcConfigDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ApiSvcProcConfigDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApiSvcProcConfigDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.apiSvcProcConfig).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
