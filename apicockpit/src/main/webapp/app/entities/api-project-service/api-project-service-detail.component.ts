import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApiProjectService } from 'app/shared/model/api-project-service.model';

@Component({
    selector: 'jhi-api-project-service-detail',
    templateUrl: './api-project-service-detail.component.html'
})
export class ApiProjectServiceDetailComponent implements OnInit {
    apiProjectService: IApiProjectService;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ apiProjectService }) => {
            this.apiProjectService = apiProjectService;
        });
    }

    previousState() {
        window.history.back();
    }
}
