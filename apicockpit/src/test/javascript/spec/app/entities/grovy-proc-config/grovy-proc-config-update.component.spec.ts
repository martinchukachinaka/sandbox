/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { GrovyProcConfigUpdateComponent } from 'app/entities/grovy-proc-config/grovy-proc-config-update.component';
import { GrovyProcConfigService } from 'app/entities/grovy-proc-config/grovy-proc-config.service';
import { GrovyProcConfig } from 'app/shared/model/grovy-proc-config.model';

describe('Component Tests', () => {
    describe('GrovyProcConfig Management Update Component', () => {
        let comp: GrovyProcConfigUpdateComponent;
        let fixture: ComponentFixture<GrovyProcConfigUpdateComponent>;
        let service: GrovyProcConfigService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [GrovyProcConfigUpdateComponent]
            })
                .overrideTemplate(GrovyProcConfigUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GrovyProcConfigUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrovyProcConfigService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new GrovyProcConfig(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.grovyProcConfig = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new GrovyProcConfig();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.grovyProcConfig = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
