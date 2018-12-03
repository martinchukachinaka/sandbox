import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDbProcConfig } from 'app/shared/model/db-proc-config.model';
import { Principal } from 'app/core';
import { DbProcConfigService } from './db-proc-config.service';

@Component({
    selector: 'jhi-db-proc-config',
    templateUrl: './db-proc-config.component.html'
})
export class DbProcConfigComponent implements OnInit, OnDestroy {
    dbProcConfigs: IDbProcConfig[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private dbProcConfigService: DbProcConfigService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.dbProcConfigService.query().subscribe(
            (res: HttpResponse<IDbProcConfig[]>) => {
                this.dbProcConfigs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDbProcConfigs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDbProcConfig) {
        return item.id;
    }

    registerChangeInDbProcConfigs() {
        this.eventSubscriber = this.eventManager.subscribe('dbProcConfigListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
