import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IApiProjectAuthConfig } from 'app/shared/model/api-project-auth-config.model';
import { ApiProjectAuthConfigService } from './api-project-auth-config.service';
import { IApiProject } from 'app/shared/model/api-project.model';
import { ApiProjectService } from 'app/entities/api-project';

@Component({
    selector: 'jhi-api-project-auth-config-update',
    templateUrl: './api-project-auth-config-update.component.html'
})
export class ApiProjectAuthConfigUpdateComponent implements OnInit {
    apiProjectAuthConfig: IApiProjectAuthConfig;
    isSaving: boolean;

    apiprojects: IApiProject[];
    dateCreated: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private apiProjectAuthConfigService: ApiProjectAuthConfigService,
        private apiProjectService: ApiProjectService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ apiProjectAuthConfig }) => {
            this.apiProjectAuthConfig = apiProjectAuthConfig;
            this.dateCreated =
                this.apiProjectAuthConfig.dateCreated != null ? this.apiProjectAuthConfig.dateCreated.format(DATE_TIME_FORMAT) : null;
        });
        this.apiProjectService.query().subscribe(
            (res: HttpResponse<IApiProject[]>) => {
                this.apiprojects = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.apiProjectAuthConfig.dateCreated = this.dateCreated != null ? moment(this.dateCreated, DATE_TIME_FORMAT) : null;
        if (this.apiProjectAuthConfig.id !== undefined) {
            this.subscribeToSaveResponse(this.apiProjectAuthConfigService.update(this.apiProjectAuthConfig));
        } else {
            this.subscribeToSaveResponse(this.apiProjectAuthConfigService.create(this.apiProjectAuthConfig));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IApiProjectAuthConfig>>) {
        result.subscribe(
            (res: HttpResponse<IApiProjectAuthConfig>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackApiProjectById(index: number, item: IApiProject) {
        return item.id;
    }
}
