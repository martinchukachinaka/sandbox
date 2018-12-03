import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApiServiceConfig } from 'app/shared/model/api-service-config.model';
import { ApiServiceConfigService } from './api-service-config.service';

@Component({
    selector: 'jhi-api-service-config-delete-dialog',
    templateUrl: './api-service-config-delete-dialog.component.html'
})
export class ApiServiceConfigDeleteDialogComponent {
    apiServiceConfig: IApiServiceConfig;

    constructor(
        private apiServiceConfigService: ApiServiceConfigService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.apiServiceConfigService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'apiServiceConfigListModification',
                content: 'Deleted an apiServiceConfig'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-api-service-config-delete-popup',
    template: ''
})
export class ApiServiceConfigDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ apiServiceConfig }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ApiServiceConfigDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.apiServiceConfig = apiServiceConfig;
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
