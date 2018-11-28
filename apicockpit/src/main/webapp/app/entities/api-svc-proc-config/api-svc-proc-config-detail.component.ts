import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApiSvcProcConfig } from 'app/shared/model/api-svc-proc-config.model';

@Component({
    selector: 'jhi-api-svc-proc-config-detail',
    templateUrl: './api-svc-proc-config-detail.component.html'
})
export class ApiSvcProcConfigDetailComponent implements OnInit {
    apiSvcProcConfig: IApiSvcProcConfig;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ apiSvcProcConfig }) => {
            this.apiSvcProcConfig = apiSvcProcConfig;
        });
    }

    previousState() {
        window.history.back();
    }
}
