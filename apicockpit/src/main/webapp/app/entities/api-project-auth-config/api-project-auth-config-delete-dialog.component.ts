import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApiProjectAuthConfig } from 'app/shared/model/api-project-auth-config.model';
import { ApiProjectAuthConfigService } from './api-project-auth-config.service';

@Component({
    selector: 'jhi-api-project-auth-config-delete-dialog',
    templateUrl: './api-project-auth-config-delete-dialog.component.html'
})
export class ApiProjectAuthConfigDeleteDialogComponent {
    apiProjectAuthConfig: IApiProjectAuthConfig;

    constructor(
        private apiProjectAuthConfigService: ApiProjectAuthConfigService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.apiProjectAuthConfigService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'apiProjectAuthConfigListModification',
                content: 'Deleted an apiProjectAuthConfig'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-api-project-auth-config-delete-popup',
    template: ''
})
export class ApiProjectAuthConfigDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ apiProjectAuthConfig }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ApiProjectAuthConfigDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.apiProjectAuthConfig = apiProjectAuthConfig;
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
