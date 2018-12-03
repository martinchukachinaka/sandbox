/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ApicockpitTestModule } from '../../../test.module';
import { ApiServiceGroupDeleteDialogComponent } from 'app/entities/api-service-group/api-service-group-delete-dialog.component';
import { ApiServiceGroupService } from 'app/entities/api-service-group/api-service-group.service';

describe('Component Tests', () => {
    describe('ApiServiceGroup Management Delete Component', () => {
        let comp: ApiServiceGroupDeleteDialogComponent;
        let fixture: ComponentFixture<ApiServiceGroupDeleteDialogComponent>;
        let service: ApiServiceGroupService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [ApiServiceGroupDeleteDialogComponent]
            })
                .overrideTemplate(ApiServiceGroupDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApiServiceGroupDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApiServiceGroupService);
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
