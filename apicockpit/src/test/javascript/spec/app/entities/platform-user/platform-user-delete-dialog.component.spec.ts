/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ApicockpitTestModule } from '../../../test.module';
import { PlatformUserDeleteDialogComponent } from 'app/entities/platform-user/platform-user-delete-dialog.component';
import { PlatformUserService } from 'app/entities/platform-user/platform-user.service';

describe('Component Tests', () => {
    describe('PlatformUser Management Delete Component', () => {
        let comp: PlatformUserDeleteDialogComponent;
        let fixture: ComponentFixture<PlatformUserDeleteDialogComponent>;
        let service: PlatformUserService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [PlatformUserDeleteDialogComponent]
            })
                .overrideTemplate(PlatformUserDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PlatformUserDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlatformUserService);
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
