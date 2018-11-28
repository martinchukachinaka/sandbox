/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { ApiServiceConfigDetailComponent } from 'app/entities/api-service-config/api-service-config-detail.component';
import { ApiServiceConfig } from 'app/shared/model/api-service-config.model';

describe('Component Tests', () => {
    describe('ApiServiceConfig Management Detail Component', () => {
        let comp: ApiServiceConfigDetailComponent;
        let fixture: ComponentFixture<ApiServiceConfigDetailComponent>;
        const route = ({ data: of({ apiServiceConfig: new ApiServiceConfig(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [ApiServiceConfigDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ApiServiceConfigDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApiServiceConfigDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.apiServiceConfig).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
