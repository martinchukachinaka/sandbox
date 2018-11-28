/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { GrovyProcConfigDetailComponent } from 'app/entities/grovy-proc-config/grovy-proc-config-detail.component';
import { GrovyProcConfig } from 'app/shared/model/grovy-proc-config.model';

describe('Component Tests', () => {
    describe('GrovyProcConfig Management Detail Component', () => {
        let comp: GrovyProcConfigDetailComponent;
        let fixture: ComponentFixture<GrovyProcConfigDetailComponent>;
        const route = ({ data: of({ grovyProcConfig: new GrovyProcConfig(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [GrovyProcConfigDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GrovyProcConfigDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GrovyProcConfigDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.grovyProcConfig).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
