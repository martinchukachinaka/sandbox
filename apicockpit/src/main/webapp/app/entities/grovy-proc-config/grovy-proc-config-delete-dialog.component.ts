import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGrovyProcConfig } from 'app/shared/model/grovy-proc-config.model';
import { GrovyProcConfigService } from './grovy-proc-config.service';

@Component({
    selector: 'jhi-grovy-proc-config-delete-dialog',
    templateUrl: './grovy-proc-config-delete-dialog.component.html'
})
export class GrovyProcConfigDeleteDialogComponent {
    grovyProcConfig: IGrovyProcConfig;

    constructor(
        private grovyProcConfigService: GrovyProcConfigService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.grovyProcConfigService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'grovyProcConfigListModification',
                content: 'Deleted an grovyProcConfig'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-grovy-proc-config-delete-popup',
    template: ''
})
export class GrovyProcConfigDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ grovyProcConfig }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GrovyProcConfigDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.grovyProcConfig = grovyProcConfig;
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
