import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IApiSvcProcConfig } from 'app/shared/model/api-svc-proc-config.model';
import { Principal } from 'app/core';
import { ApiSvcProcConfigService } from './api-svc-proc-config.service';

@Component({
    selector: 'jhi-api-svc-proc-config',
    templateUrl: './api-svc-proc-config.component.html'
})
export class ApiSvcProcConfigComponent implements OnInit, OnDestroy {
    apiSvcProcConfigs: IApiSvcProcConfig[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private apiSvcProcConfigService: ApiSvcProcConfigService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.apiSvcProcConfigService.query().subscribe(
            (res: HttpResponse<IApiSvcProcConfig[]>) => {
                this.apiSvcProcConfigs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInApiSvcProcConfigs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IApiSvcProcConfig) {
        return item.id;
    }

    registerChangeInApiSvcProcConfigs() {
        this.eventSubscriber = this.eventManager.subscribe('apiSvcProcConfigListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
