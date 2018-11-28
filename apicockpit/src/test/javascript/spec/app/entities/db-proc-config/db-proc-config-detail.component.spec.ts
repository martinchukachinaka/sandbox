/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { DbProcConfigDetailComponent } from 'app/entities/db-proc-config/db-proc-config-detail.component';
import { DbProcConfig } from 'app/shared/model/db-proc-config.model';

describe('Component Tests', () => {
    describe('DbProcConfig Management Detail Component', () => {
        let comp: DbProcConfigDetailComponent;
        let fixture: ComponentFixture<DbProcConfigDetailComponent>;
        const route = ({ data: of({ dbProcConfig: new DbProcConfig(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [DbProcConfigDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DbProcConfigDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DbProcConfigDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dbProcConfig).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
