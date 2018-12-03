import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApiServiceGroup } from 'app/shared/model/api-service-group.model';

@Component({
    selector: 'jhi-api-service-group-detail',
    templateUrl: './api-service-group-detail.component.html'
})
export class ApiServiceGroupDetailComponent implements OnInit {
    apiServiceGroup: IApiServiceGroup;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ apiServiceGroup }) => {
            this.apiServiceGroup = apiServiceGroup;
        });
    }

    previousState() {
        window.history.back();
    }
}
