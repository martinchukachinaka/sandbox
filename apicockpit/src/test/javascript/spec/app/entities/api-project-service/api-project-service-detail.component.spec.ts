/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { ApiProjectServiceDetailComponent } from 'app/entities/api-project-service/api-project-service-detail.component';
import { ApiProjectService } from 'app/shared/model/api-project-service.model';

describe('Component Tests', () => {
    describe('ApiProjectService Management Detail Component', () => {
        let comp: ApiProjectServiceDetailComponent;
        let fixture: ComponentFixture<ApiProjectServiceDetailComponent>;
        const route = ({ data: of({ apiProjectService: new ApiProjectService(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [ApiProjectServiceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ApiProjectServiceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApiProjectServiceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.apiProjectService).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
