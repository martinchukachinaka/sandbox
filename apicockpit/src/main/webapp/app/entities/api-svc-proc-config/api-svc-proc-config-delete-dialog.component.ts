import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApiSvcProcConfig } from 'app/shared/model/api-svc-proc-config.model';
import { ApiSvcProcConfigService } from './api-svc-proc-config.service';

@Component({
    selector: 'jhi-api-svc-proc-config-delete-dialog',
    templateUrl: './api-svc-proc-config-delete-dialog.component.html'
})
export class ApiSvcProcConfigDeleteDialogComponent {
    apiSvcProcConfig: IApiSvcProcConfig;

    constructor(
        private apiSvcProcConfigService: ApiSvcProcConfigService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.apiSvcProcConfigService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'apiSvcProcConfigListModification',
                content: 'Deleted an apiSvcProcConfig'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-api-svc-proc-config-delete-popup',
    template: ''
})
export class ApiSvcProcConfigDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ apiSvcProcConfig }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ApiSvcProcConfigDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.apiSvcProcConfig = apiSvcProcConfig;
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
