import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IApiConsumerProfile } from 'app/shared/model/api-consumer-profile.model';
import { ApiConsumerProfileService } from './api-consumer-profile.service';
import { IPlatformUser } from 'app/shared/model/platform-user.model';
import { PlatformUserService } from 'app/entities/platform-user';
import { IApiPublisherProfile } from 'app/shared/model/api-publisher-profile.model';
import { ApiPublisherProfileService } from 'app/entities/api-publisher-profile';

@Component({
    selector: 'jhi-api-consumer-profile-update',
    templateUrl: './api-consumer-profile-update.component.html'
})
export class ApiConsumerProfileUpdateComponent implements OnInit {
    apiConsumerProfile: IApiConsumerProfile;
    isSaving: boolean;

    platformusers: IPlatformUser[];

    apipublisherprofiles: IApiPublisherProfile[];
    dateCreated: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private apiConsumerProfileService: ApiConsumerProfileService,
        private platformUserService: PlatformUserService,
        private apiPublisherProfileService: ApiPublisherProfileService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ apiConsumerProfile }) => {
            this.apiConsumerProfile = apiConsumerProfile;
            this.dateCreated =
                this.apiConsumerProfile.dateCreated != null ? this.apiConsumerProfile.dateCreated.format(DATE_TIME_FORMAT) : null;
        });
        this.platformUserService.query().subscribe(
            (res: HttpResponse<IPlatformUser[]>) => {
                this.platformusers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        this.apiConsumerProfile.dateCreated = this.dateCreated != null ? moment(this.dateCreated, DATE_TIME_FORMAT) : null;
        if (this.apiConsumerProfile.id !== undefined) {
            this.subscribeToSaveResponse(this.apiConsumerProfileService.update(this.apiConsumerProfile));
        } else {
            this.subscribeToSaveResponse(this.apiConsumerProfileService.create(this.apiConsumerProfile));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IApiConsumerProfile>>) {
        result.subscribe((res: HttpResponse<IApiConsumerProfile>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackApiPublisherProfileById(index: number, item: IApiPublisherProfile) {
        return item.id;
    }
}
