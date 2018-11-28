import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IDbProcConfig } from 'app/shared/model/db-proc-config.model';
import { DbProcConfigService } from './db-proc-config.service';
import { IApiSvcProcConfig } from 'app/shared/model/api-svc-proc-config.model';
import { ApiSvcProcConfigService } from 'app/entities/api-svc-proc-config';

@Component({
    selector: 'jhi-db-proc-config-update',
    templateUrl: './db-proc-config-update.component.html'
})
export class DbProcConfigUpdateComponent implements OnInit {
    dbProcConfig: IDbProcConfig;
    isSaving: boolean;

    configs: IApiSvcProcConfig[];
    dateCreated: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private dbProcConfigService: DbProcConfigService,
        private apiSvcProcConfigService: ApiSvcProcConfigService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dbProcConfig }) => {
            this.dbProcConfig = dbProcConfig;
            this.dateCreated = this.dbProcConfig.dateCreated != null ? this.dbProcConfig.dateCreated.format(DATE_TIME_FORMAT) : null;
        });
        this.apiSvcProcConfigService.query({ filter: 'dbprocconfig-is-null' }).subscribe(
            (res: HttpResponse<IApiSvcProcConfig[]>) => {
                if (!this.dbProcConfig.configId) {
                    this.configs = res.body;
                } else {
                    this.apiSvcProcConfigService.find(this.dbProcConfig.configId).subscribe(
                        (subRes: HttpResponse<IApiSvcProcConfig>) => {
                            this.configs = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.dbProcConfig.dateCreated = this.dateCreated != null ? moment(this.dateCreated, DATE_TIME_FORMAT) : null;
        if (this.dbProcConfig.id !== undefined) {
            this.subscribeToSaveResponse(this.dbProcConfigService.update(this.dbProcConfig));
        } else {
            this.subscribeToSaveResponse(this.dbProcConfigService.create(this.dbProcConfig));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDbProcConfig>>) {
        result.subscribe((res: HttpResponse<IDbProcConfig>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackApiSvcProcConfigById(index: number, item: IApiSvcProcConfig) {
        return item.id;
    }
}
