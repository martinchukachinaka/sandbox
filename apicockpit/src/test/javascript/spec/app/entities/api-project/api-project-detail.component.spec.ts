/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { ApiProjectDetailComponent } from 'app/entities/api-project/api-project-detail.component';
import { ApiProject } from 'app/shared/model/api-project.model';

describe('Component Tests', () => {
    describe('ApiProject Management Detail Component', () => {
        let comp: ApiProjectDetailComponent;
        let fixture: ComponentFixture<ApiProjectDetailComponent>;
        const route = ({ data: of({ apiProject: new ApiProject(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [ApiProjectDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ApiProjectDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApiProjectDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.apiProject).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
