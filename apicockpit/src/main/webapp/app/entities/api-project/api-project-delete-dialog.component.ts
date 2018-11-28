import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApiProject } from 'app/shared/model/api-project.model';
import { ApiProjectService } from './api-project.service';

@Component({
    selector: 'jhi-api-project-delete-dialog',
    templateUrl: './api-project-delete-dialog.component.html'
})
export class ApiProjectDeleteDialogComponent {
    apiProject: IApiProject;

    constructor(private apiProjectService: ApiProjectService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.apiProjectService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'apiProjectListModification',
                content: 'Deleted an apiProject'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-api-project-delete-popup',
    template: ''
})
export class ApiProjectDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ apiProject }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ApiProjectDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.apiProject = apiProject;
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
