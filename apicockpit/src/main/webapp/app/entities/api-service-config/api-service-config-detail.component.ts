import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApiServiceConfig } from 'app/shared/model/api-service-config.model';

@Component({
    selector: 'jhi-api-service-config-detail',
    templateUrl: './api-service-config-detail.component.html'
})
export class ApiServiceConfigDetailComponent implements OnInit {
    apiServiceConfig: IApiServiceConfig;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ apiServiceConfig }) => {
            this.apiServiceConfig = apiServiceConfig;
        });
    }

    previousState() {
        window.history.back();
    }
}
