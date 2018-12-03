import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlatformUser } from 'app/shared/model/platform-user.model';
import { PlatformUserService } from './platform-user.service';

@Component({
    selector: 'jhi-platform-user-delete-dialog',
    templateUrl: './platform-user-delete-dialog.component.html'
})
export class PlatformUserDeleteDialogComponent {
    platformUser: IPlatformUser;

    constructor(
        private platformUserService: PlatformUserService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.platformUserService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'platformUserListModification',
                content: 'Deleted an platformUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-platform-user-delete-popup',
    template: ''
})
export class PlatformUserDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ platformUser }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PlatformUserDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.platformUser = platformUser;
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
