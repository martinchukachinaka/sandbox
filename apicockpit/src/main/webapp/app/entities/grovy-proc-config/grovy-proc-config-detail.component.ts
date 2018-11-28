import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGrovyProcConfig } from 'app/shared/model/grovy-proc-config.model';

@Component({
    selector: 'jhi-grovy-proc-config-detail',
    templateUrl: './grovy-proc-config-detail.component.html'
})
export class GrovyProcConfigDetailComponent implements OnInit {
    grovyProcConfig: IGrovyProcConfig;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ grovyProcConfig }) => {
            this.grovyProcConfig = grovyProcConfig;
        });
    }

    previousState() {
        window.history.back();
    }
}
