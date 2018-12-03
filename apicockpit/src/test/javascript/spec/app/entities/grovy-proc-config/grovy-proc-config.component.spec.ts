/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ApicockpitTestModule } from '../../../test.module';
import { GrovyProcConfigComponent } from 'app/entities/grovy-proc-config/grovy-proc-config.component';
import { GrovyProcConfigService } from 'app/entities/grovy-proc-config/grovy-proc-config.service';
import { GrovyProcConfig } from 'app/shared/model/grovy-proc-config.model';

describe('Component Tests', () => {
    describe('GrovyProcConfig Management Component', () => {
        let comp: GrovyProcConfigComponent;
        let fixture: ComponentFixture<GrovyProcConfigComponent>;
        let service: GrovyProcConfigService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [GrovyProcConfigComponent],
                providers: []
            })
                .overrideTemplate(GrovyProcConfigComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GrovyProcConfigComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrovyProcConfigService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new GrovyProcConfig(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.grovyProcConfigs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
