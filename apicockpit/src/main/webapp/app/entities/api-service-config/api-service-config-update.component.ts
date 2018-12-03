import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IApiServiceConfig } from 'app/shared/model/api-service-config.model';
import { ApiServiceConfigService } from './api-service-config.service';
import { IApiServiceGroup } from 'app/shared/model/api-service-group.model';
import { ApiServiceGroupService } from 'app/entities/api-service-group';

@Component({
    selector: 'jhi-api-service-config-update',
    templateUrl: './api-service-config-update.component.html'
})
export class ApiServiceConfigUpdateComponent implements OnInit {
    apiServiceConfig: IApiServiceConfig;
    isSaving: boolean;

    apiservicegroups: IApiServiceGroup[];
    dateCreated: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private apiServiceConfigService: ApiServiceConfigService,
        private apiServiceGroupService: ApiServiceGroupService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ apiServiceConfig }) => {
            this.apiServiceConfig = apiServiceConfig;
            this.dateCreated =
                this.apiServiceConfig.dateCreated != null ? this.apiServiceConfig.dateCreated.format(DATE_TIME_FORMAT) : null;
        });
        this.apiServiceGroupService.query().subscribe(
            (res: HttpResponse<IApiServiceGroup[]>) => {
                this.apiservicegroups = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.apiServiceConfig.dateCreated = this.dateCreated != null ? moment(this.dateCreated, DATE_TIME_FORMAT) : null;
        if (this.apiServiceConfig.id !== undefined) {
            this.subscribeToSaveResponse(this.apiServiceConfigService.update(this.apiServiceConfig));
        } else {
            this.subscribeToSaveResponse(this.apiServiceConfigService.create(this.apiServiceConfig));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IApiServiceConfig>>) {
        result.subscribe((res: HttpResponse<IApiServiceConfig>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackApiServiceGroupById(index: number, item: IApiServiceGroup) {
        return item.id;
    }
}
