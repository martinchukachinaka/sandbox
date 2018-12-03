/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ApicockpitTestModule } from '../../../test.module';
import { DbProcConfigComponent } from 'app/entities/db-proc-config/db-proc-config.component';
import { DbProcConfigService } from 'app/entities/db-proc-config/db-proc-config.service';
import { DbProcConfig } from 'app/shared/model/db-proc-config.model';

describe('Component Tests', () => {
    describe('DbProcConfig Management Component', () => {
        let comp: DbProcConfigComponent;
        let fixture: ComponentFixture<DbProcConfigComponent>;
        let service: DbProcConfigService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [DbProcConfigComponent],
                providers: []
            })
                .overrideTemplate(DbProcConfigComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DbProcConfigComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DbProcConfigService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DbProcConfig(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.dbProcConfigs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
