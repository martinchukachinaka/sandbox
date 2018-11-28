import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApiPublisherProfile } from 'app/shared/model/api-publisher-profile.model';
import { ApiPublisherProfileService } from './api-publisher-profile.service';

@Component({
    selector: 'jhi-api-publisher-profile-delete-dialog',
    templateUrl: './api-publisher-profile-delete-dialog.component.html'
})
export class ApiPublisherProfileDeleteDialogComponent {
    apiPublisherProfile: IApiPublisherProfile;

    constructor(
        private apiPublisherProfileService: ApiPublisherProfileService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.apiPublisherProfileService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'apiPublisherProfileListModification',
                content: 'Deleted an apiPublisherProfile'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-api-publisher-profile-delete-popup',
    template: ''
})
export class ApiPublisherProfileDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ apiPublisherProfile }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ApiPublisherProfileDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.apiPublisherProfile = apiPublisherProfile;
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
