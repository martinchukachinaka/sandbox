/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { ApiServiceGroupDetailComponent } from 'app/entities/api-service-group/api-service-group-detail.component';
import { ApiServiceGroup } from 'app/shared/model/api-service-group.model';

describe('Component Tests', () => {
    describe('ApiServiceGroup Management Detail Component', () => {
        let comp: ApiServiceGroupDetailComponent;
        let fixture: ComponentFixture<ApiServiceGroupDetailComponent>;
        const route = ({ data: of({ apiServiceGroup: new ApiServiceGroup(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [ApiServiceGroupDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ApiServiceGroupDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApiServiceGroupDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.apiServiceGroup).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
