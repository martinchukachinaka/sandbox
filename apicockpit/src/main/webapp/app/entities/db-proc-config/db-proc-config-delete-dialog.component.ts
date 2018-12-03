import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDbProcConfig } from 'app/shared/model/db-proc-config.model';
import { DbProcConfigService } from './db-proc-config.service';

@Component({
    selector: 'jhi-db-proc-config-delete-dialog',
    templateUrl: './db-proc-config-delete-dialog.component.html'
})
export class DbProcConfigDeleteDialogComponent {
    dbProcConfig: IDbProcConfig;

    constructor(
        private dbProcConfigService: DbProcConfigService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dbProcConfigService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'dbProcConfigListModification',
                content: 'Deleted an dbProcConfig'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-db-proc-config-delete-popup',
    template: ''
})
export class DbProcConfigDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dbProcConfig }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DbProcConfigDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.dbProcConfig = dbProcConfig;
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
