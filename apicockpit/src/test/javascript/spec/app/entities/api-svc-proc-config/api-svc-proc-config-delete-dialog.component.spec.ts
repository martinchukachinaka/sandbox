/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ApicockpitTestModule } from '../../../test.module';
import { ApiSvcProcConfigDeleteDialogComponent } from 'app/entities/api-svc-proc-config/api-svc-proc-config-delete-dialog.component';
import { ApiSvcProcConfigService } from 'app/entities/api-svc-proc-config/api-svc-proc-config.service';

describe('Component Tests', () => {
    describe('ApiSvcProcConfig Management Delete Component', () => {
        let comp: ApiSvcProcConfigDeleteDialogComponent;
        let fixture: ComponentFixture<ApiSvcProcConfigDeleteDialogComponent>;
        let service: ApiSvcProcConfigService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [ApiSvcProcConfigDeleteDialogComponent]
            })
                .overrideTemplate(ApiSvcProcConfigDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApiSvcProcConfigDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApiSvcProcConfigService);
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
