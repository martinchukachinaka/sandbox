import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IGrovyProcConfig } from 'app/shared/model/grovy-proc-config.model';
import { Principal } from 'app/core';
import { GrovyProcConfigService } from './grovy-proc-config.service';

@Component({
    selector: 'jhi-grovy-proc-config',
    templateUrl: './grovy-proc-config.component.html'
})
export class GrovyProcConfigComponent implements OnInit, OnDestroy {
    grovyProcConfigs: IGrovyProcConfig[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private grovyProcConfigService: GrovyProcConfigService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.grovyProcConfigService.query().subscribe(
            (res: HttpResponse<IGrovyProcConfig[]>) => {
                this.grovyProcConfigs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInGrovyProcConfigs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IGrovyProcConfig) {
        return item.id;
    }

    registerChangeInGrovyProcConfigs() {
        this.eventSubscriber = this.eventManager.subscribe('grovyProcConfigListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
