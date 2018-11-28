import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApiConsumerProfile } from 'app/shared/model/api-consumer-profile.model';

@Component({
    selector: 'jhi-api-consumer-profile-detail',
    templateUrl: './api-consumer-profile-detail.component.html'
})
export class ApiConsumerProfileDetailComponent implements OnInit {
    apiConsumerProfile: IApiConsumerProfile;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ apiConsumerProfile }) => {
            this.apiConsumerProfile = apiConsumerProfile;
        });
    }

    previousState() {
        window.history.back();
    }
}
