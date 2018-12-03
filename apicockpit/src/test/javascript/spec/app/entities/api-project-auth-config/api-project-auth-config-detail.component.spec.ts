/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { ApiProjectAuthConfigDetailComponent } from 'app/entities/api-project-auth-config/api-project-auth-config-detail.component';
import { ApiProjectAuthConfig } from 'app/shared/model/api-project-auth-config.model';

describe('Component Tests', () => {
    describe('ApiProjectAuthConfig Management Detail Component', () => {
        let comp: ApiProjectAuthConfigDetailComponent;
        let fixture: ComponentFixture<ApiProjectAuthConfigDetailComponent>;
        const route = ({ data: of({ apiProjectAuthConfig: new ApiProjectAuthConfig(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [ApiProjectAuthConfigDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ApiProjectAuthConfigDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApiProjectAuthConfigDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.apiProjectAuthConfig).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
