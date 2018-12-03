/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ApicockpitTestModule } from '../../../test.module';
import { DbProcConfigUpdateComponent } from 'app/entities/db-proc-config/db-proc-config-update.component';
import { DbProcConfigService } from 'app/entities/db-proc-config/db-proc-config.service';
import { DbProcConfig } from 'app/shared/model/db-proc-config.model';

describe('Component Tests', () => {
    describe('DbProcConfig Management Update Component', () => {
        let comp: DbProcConfigUpdateComponent;
        let fixture: ComponentFixture<DbProcConfigUpdateComponent>;
        let service: DbProcConfigService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [DbProcConfigUpdateComponent]
            })
                .overrideTemplate(DbProcConfigUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DbProcConfigUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DbProcConfigService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new DbProcConfig(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.dbProcConfig = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new DbProcConfig();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.dbProcConfig = entity;
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
