import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IApiSvcProcConfig } from 'app/shared/model/api-svc-proc-config.model';
import { ApiSvcProcConfigService } from './api-svc-proc-config.service';
import { IApiServiceConfig } from 'app/shared/model/api-service-config.model';
import { ApiServiceConfigService } from 'app/entities/api-service-config';

@Component({
    selector: 'jhi-api-svc-proc-config-update',
    templateUrl: './api-svc-proc-config-update.component.html'
})
export class ApiSvcProcConfigUpdateComponent implements OnInit {
    apiSvcProcConfig: IApiSvcProcConfig;
    isSaving: boolean;

    apiserviceconfigs: IApiServiceConfig[];
    dateCreated: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private apiSvcProcConfigService: ApiSvcProcConfigService,
        private apiServiceConfigService: ApiServiceConfigService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ apiSvcProcConfig }) => {
            this.apiSvcProcConfig = apiSvcProcConfig;
            this.dateCreated =
                this.apiSvcProcConfig.dateCreated != null ? this.apiSvcProcConfig.dateCreated.format(DATE_TIME_FORMAT) : null;
        });
        this.apiServiceConfigService.query().subscribe(
            (res: HttpResponse<IApiServiceConfig[]>) => {
                this.apiserviceconfigs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.apiSvcProcConfig.dateCreated = this.dateCreated != null ? moment(this.dateCreated, DATE_TIME_FORMAT) : null;
        if (this.apiSvcProcConfig.id !== undefined) {
            this.subscribeToSaveResponse(this.apiSvcProcConfigService.update(this.apiSvcProcConfig));
        } else {
            this.subscribeToSaveResponse(this.apiSvcProcConfigService.create(this.apiSvcProcConfig));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IApiSvcProcConfig>>) {
        result.subscribe((res: HttpResponse<IApiSvcProcConfig>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackApiServiceConfigById(index: number, item: IApiServiceConfig) {
        return item.id;
    }
}
