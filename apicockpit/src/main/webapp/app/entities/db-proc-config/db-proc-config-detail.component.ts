import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDbProcConfig } from 'app/shared/model/db-proc-config.model';

@Component({
    selector: 'jhi-db-proc-config-detail',
    templateUrl: './db-proc-config-detail.component.html'
})
export class DbProcConfigDetailComponent implements OnInit {
    dbProcConfig: IDbProcConfig;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dbProcConfig }) => {
            this.dbProcConfig = dbProcConfig;
        });
    }

    previousState() {
        window.history.back();
    }
}
