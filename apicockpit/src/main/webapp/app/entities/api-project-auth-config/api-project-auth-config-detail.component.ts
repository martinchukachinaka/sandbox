import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApiProjectAuthConfig } from 'app/shared/model/api-project-auth-config.model';

@Component({
    selector: 'jhi-api-project-auth-config-detail',
    templateUrl: './api-project-auth-config-detail.component.html'
})
export class ApiProjectAuthConfigDetailComponent implements OnInit {
    apiProjectAuthConfig: IApiProjectAuthConfig;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ apiProjectAuthConfig }) => {
            this.apiProjectAuthConfig = apiProjectAuthConfig;
        });
    }

    previousState() {
        window.history.back();
    }
}
