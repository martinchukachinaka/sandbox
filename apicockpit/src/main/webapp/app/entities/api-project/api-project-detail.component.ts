import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApiProject } from 'app/shared/model/api-project.model';

@Component({
    selector: 'jhi-api-project-detail',
    templateUrl: './api-project-detail.component.html'
})
export class ApiProjectDetailComponent implements OnInit {
    apiProject: IApiProject;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ apiProject }) => {
            this.apiProject = apiProject;
        });
    }

    previousState() {
        window.history.back();
    }
}
