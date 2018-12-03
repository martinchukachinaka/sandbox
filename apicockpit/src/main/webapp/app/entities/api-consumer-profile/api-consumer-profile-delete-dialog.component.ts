import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApiConsumerProfile } from 'app/shared/model/api-consumer-profile.model';
import { ApiConsumerProfileService } from './api-consumer-profile.service';

@Component({
    selector: 'jhi-api-consumer-profile-delete-dialog',
    templateUrl: './api-consumer-profile-delete-dialog.component.html'
})
export class ApiConsumerProfileDeleteDialogComponent {
    apiConsumerProfile: IApiConsumerProfile;

    constructor(
        private apiConsumerProfileService: ApiConsumerProfileService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.apiConsumerProfileService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'apiConsumerProfileListModification',
                content: 'Deleted an apiConsumerProfile'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-api-consumer-profile-delete-popup',
    template: ''
})
export class ApiConsumerProfileDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ apiConsumerProfile }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ApiConsumerProfileDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.apiConsumerProfile = apiConsumerProfile;
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
