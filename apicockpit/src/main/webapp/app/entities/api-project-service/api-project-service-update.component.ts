import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IApiProjectService } from 'app/shared/model/api-project-service.model';
import { ApiProjectServiceService } from './api-project-service.service';
import { IApiServiceConfig } from 'app/shared/model/api-service-config.model';
import { ApiServiceConfigService } from 'app/entities/api-service-config';

@Component({
    selector: 'jhi-api-project-service-update',
    templateUrl: './api-project-service-update.component.html'
})
export class ApiProjectServiceUpdateComponent implements OnInit {
    apiProjectService: IApiProjectService;
    isSaving: boolean;

    apiserviceconfigs: IApiServiceConfig[];
    dateCreated: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private apiProjectServiceService: ApiProjectServiceService,
        private apiServiceConfigService: ApiServiceConfigService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ apiProjectService }) => {
            this.apiProjectService = apiProjectService;
            this.dateCreated =
                this.apiProjectService.dateCreated != null ? this.apiProjectService.dateCreated.format(DATE_TIME_FORMAT) : null;
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
        this.apiProjectService.dateCreated = this.dateCreated != null ? moment(this.dateCreated, DATE_TIME_FORMAT) : null;
        if (this.apiProjectService.id !== undefined) {
            this.subscribeToSaveResponse(this.apiProjectServiceService.update(this.apiProjectService));
        } else {
            this.subscribeToSaveResponse(this.apiProjectServiceService.create(this.apiProjectService));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IApiProjectService>>) {
        result.subscribe((res: HttpResponse<IApiProjectService>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
