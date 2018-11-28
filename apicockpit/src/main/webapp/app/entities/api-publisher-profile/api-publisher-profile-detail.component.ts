import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApiPublisherProfile } from 'app/shared/model/api-publisher-profile.model';

@Component({
    selector: 'jhi-api-publisher-profile-detail',
    templateUrl: './api-publisher-profile-detail.component.html'
})
export class ApiPublisherProfileDetailComponent implements OnInit {
    apiPublisherProfile: IApiPublisherProfile;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ apiPublisherProfile }) => {
            this.apiPublisherProfile = apiPublisherProfile;
        });
    }

    previousState() {
        window.history.back();
    }
}
