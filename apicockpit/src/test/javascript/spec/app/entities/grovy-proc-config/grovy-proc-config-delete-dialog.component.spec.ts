/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ApicockpitTestModule } from '../../../test.module';
import { GrovyProcConfigDeleteDialogComponent } from 'app/entities/grovy-proc-config/grovy-proc-config-delete-dialog.component';
import { GrovyProcConfigService } from 'app/entities/grovy-proc-config/grovy-proc-config.service';

describe('Component Tests', () => {
    describe('GrovyProcConfig Management Delete Component', () => {
        let comp: GrovyProcConfigDeleteDialogComponent;
        let fixture: ComponentFixture<GrovyProcConfigDeleteDialogComponent>;
        let service: GrovyProcConfigService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [GrovyProcConfigDeleteDialogComponent]
            })
                .overrideTemplate(GrovyProcConfigDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GrovyProcConfigDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrovyProcConfigService);
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
