/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ApicockpitTestModule } from '../../../test.module';
import { ApiProjectServiceDeleteDialogComponent } from 'app/entities/api-project-service/api-project-service-delete-dialog.component';
import { ApiProjectServiceService } from 'app/entities/api-project-service/api-project-service.service';

describe('Component Tests', () => {
    describe('ApiProjectService Management Delete Component', () => {
        let comp: ApiProjectServiceDeleteDialogComponent;
        let fixture: ComponentFixture<ApiProjectServiceDeleteDialogComponent>;
        let service: ApiProjectServiceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [ApiProjectServiceDeleteDialogComponent]
            })
                .overrideTemplate(ApiProjectServiceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApiProjectServiceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApiProjectServiceService);
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
