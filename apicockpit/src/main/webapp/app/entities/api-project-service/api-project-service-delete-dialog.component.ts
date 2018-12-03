import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApiProjectService } from 'app/shared/model/api-project-service.model';
import { ApiProjectServiceService } from './api-project-service.service';

@Component({
    selector: 'jhi-api-project-service-delete-dialog',
    templateUrl: './api-project-service-delete-dialog.component.html'
})
export class ApiProjectServiceDeleteDialogComponent {
    apiProjectService: IApiProjectService;

    constructor(
        private apiProjectServiceService: ApiProjectServiceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.apiProjectServiceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'apiProjectServiceListModification',
                content: 'Deleted an apiProjectService'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-api-project-service-delete-popup',
    template: ''
})
export class ApiProjectServiceDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ apiProjectService }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ApiProjectServiceDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.apiProjectService = apiProjectService;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
