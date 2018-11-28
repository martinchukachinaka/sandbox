/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { ApiPublisherProfileDetailComponent } from 'app/entities/api-publisher-profile/api-publisher-profile-detail.component';
import { ApiPublisherProfile } from 'app/shared/model/api-publisher-profile.model';

describe('Component Tests', () => {
    describe('ApiPublisherProfile Management Detail Component', () => {
        let comp: ApiPublisherProfileDetailComponent;
        let fixture: ComponentFixture<ApiPublisherProfileDetailComponent>;
        const route = ({ data: of({ apiPublisherProfile: new ApiPublisherProfile(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [ApiPublisherProfileDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ApiPublisherProfileDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApiPublisherProfileDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.apiPublisherProfile).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
