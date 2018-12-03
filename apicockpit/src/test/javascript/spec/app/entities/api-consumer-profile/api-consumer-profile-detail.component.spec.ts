/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { ApiConsumerProfileDetailComponent } from 'app/entities/api-consumer-profile/api-consumer-profile-detail.component';
import { ApiConsumerProfile } from 'app/shared/model/api-consumer-profile.model';

describe('Component Tests', () => {
    describe('ApiConsumerProfile Management Detail Component', () => {
        let comp: ApiConsumerProfileDetailComponent;
        let fixture: ComponentFixture<ApiConsumerProfileDetailComponent>;
        const route = ({ data: of({ apiConsumerProfile: new ApiConsumerProfile(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [ApiConsumerProfileDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ApiConsumerProfileDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApiConsumerProfileDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.apiConsumerProfile).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
