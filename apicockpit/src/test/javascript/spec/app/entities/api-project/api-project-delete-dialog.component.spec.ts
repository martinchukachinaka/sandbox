/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ApicockpitTestModule } from '../../../test.module';
import { ApiProjectDeleteDialogComponent } from 'app/entities/api-project/api-project-delete-dialog.component';
import { ApiProjectService } from 'app/entities/api-project/api-project.service';

describe('Component Tests', () => {
    describe('ApiProject Management Delete Component', () => {
        let comp: ApiProjectDeleteDialogComponent;
        let fixture: ComponentFixture<ApiProjectDeleteDialogComponent>;
        let service: ApiProjectService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [ApiProjectDeleteDialogComponent]
            })
                .overrideTemplate(ApiProjectDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApiProjectDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApiProjectService);
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
