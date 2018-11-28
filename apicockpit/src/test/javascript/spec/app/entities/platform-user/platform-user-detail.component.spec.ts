/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { PlatformUserDetailComponent } from 'app/entities/platform-user/platform-user-detail.component';
import { PlatformUser } from 'app/shared/model/platform-user.model';

describe('Component Tests', () => {
    describe('PlatformUser Management Detail Component', () => {
        let comp: PlatformUserDetailComponent;
        let fixture: ComponentFixture<PlatformUserDetailComponent>;
        const route = ({ data: of({ platformUser: new PlatformUser(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [PlatformUserDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PlatformUserDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PlatformUserDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.platformUser).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
