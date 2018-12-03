import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPlatformUser } from 'app/shared/model/platform-user.model';
import { Principal } from 'app/core';
import { PlatformUserService } from './platform-user.service';

@Component({
    selector: 'jhi-platform-user',
    templateUrl: './platform-user.component.html'
})
export class PlatformUserComponent implements OnInit, OnDestroy {
    platformUsers: IPlatformUser[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private platformUserService: PlatformUserService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.platformUserService.query().subscribe(
            (res: HttpResponse<IPlatformUser[]>) => {
                this.platformUsers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPlatformUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPlatformUser) {
        return item.id;
    }

    registerChangeInPlatformUsers() {
        this.eventSubscriber = this.eventManager.subscribe('platformUserListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
