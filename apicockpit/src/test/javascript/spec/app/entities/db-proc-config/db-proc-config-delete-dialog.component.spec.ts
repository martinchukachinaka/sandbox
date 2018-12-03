/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ApicockpitTestModule } from '../../../test.module';
import { DbProcConfigDeleteDialogComponent } from 'app/entities/db-proc-config/db-proc-config-delete-dialog.component';
import { DbProcConfigService } from 'app/entities/db-proc-config/db-proc-config.service';

describe('Component Tests', () => {
    describe('DbProcConfig Management Delete Component', () => {
        let comp: DbProcConfigDeleteDialogComponent;
        let fixture: ComponentFixture<DbProcConfigDeleteDialogComponent>;
        let service: DbProcConfigService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [DbProcConfigDeleteDialogComponent]
            })
                .overrideTemplate(DbProcConfigDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DbProcConfigDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DbProcConfigService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
