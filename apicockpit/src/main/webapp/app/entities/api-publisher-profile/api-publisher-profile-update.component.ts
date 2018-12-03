import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IApiPublisherProfile } from 'app/shared/model/api-publisher-profile.model';
import { ApiPublisherProfileService } from './api-publisher-profile.service';
import { IPlatformUser } from 'app/shared/model/platform-user.model';
import { PlatformUserService } from 'app/entities/platform-user';

@Component({
    selector: 'jhi-api-publisher-profile-update',
    templateUrl: './api-publisher-profile-update.component.html'
})
export class ApiPublisherProfileUpdateComponent implements OnInit {
    apiPublisherProfile: IApiPublisherProfile;
    isSaving: boolean;

    platformusers: IPlatformUser[];
    dateCreated: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private apiPublisherProfileService: ApiPublisherProfileService,
        private platformUserService: PlatformUserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ apiPublisherProfile }) => {
            this.apiPublisherProfile = apiPublisherProfile;
            this.dateCreated =
                this.apiPublisherProfile.dateCreated != null ? this.apiPublisherProfile.dateCreated.format(DATE_TIME_FORMAT) : null;
        });
        this.platformUserService.query().subscribe(
            (res: HttpResponse<IPlatformUser[]>) => {
                this.platformusers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.apiPublisherProfile.dateCreated = this.dateCreated != null ? moment(this.dateCreated, DATE_TIME_FORMAT) : null;
        if (this.apiPublisherProfile.id !== undefined) {
            this.subscribeToSaveResponse(this.apiPublisherProfileService.update(this.apiPublisherProfile));
        } else {
            this.subscribeToSaveResponse(this.apiPublisherProfileService.create(this.apiPublisherProfile));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IApiPublisherProfile>>) {
        result.subscribe((res: HttpResponse<IApiPublisherProfile>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPlatformUserById(index: number, item: IPlatformUser) {
        return item.id;
    }
}
