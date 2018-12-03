import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IApiServiceGroup } from 'app/shared/model/api-service-group.model';
import { ApiServiceGroupService } from './api-service-group.service';
import { IApiPublisherProfile } from 'app/shared/model/api-publisher-profile.model';
import { ApiPublisherProfileService } from 'app/entities/api-publisher-profile';

@Component({
    selector: 'jhi-api-service-group-update',
    templateUrl: './api-service-group-update.component.html'
})
export class ApiServiceGroupUpdateComponent implements OnInit {
    apiServiceGroup: IApiServiceGroup;
    isSaving: boolean;

    apipublisherprofiles: IApiPublisherProfile[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private apiServiceGroupService: ApiServiceGroupService,
        private apiPublisherProfileService: ApiPublisherProfileService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ apiServiceGroup }) => {
            this.apiServiceGroup = apiServiceGroup;
        });
        this.apiPublisherProfileService.query().subscribe(
            (res: HttpResponse<IApiPublisherProfile[]>) => {
                this.apipublisherprofiles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.apiServiceGroup.id !== undefined) {
            this.subscribeToSaveResponse(this.apiServiceGroupService.update(this.apiServiceGroup));
        } else {
            this.subscribeToSaveResponse(this.apiServiceGroupService.create(this.apiServiceGroup));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IApiServiceGroup>>) {
        result.subscribe((res: HttpResponse<IApiServiceGroup>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackApiPublisherProfileById(index: number, item: IApiPublisherProfile) {
        return item.id;
    }
}
