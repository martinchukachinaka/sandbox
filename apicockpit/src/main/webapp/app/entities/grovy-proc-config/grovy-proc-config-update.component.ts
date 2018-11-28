import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IGrovyProcConfig } from 'app/shared/model/grovy-proc-config.model';
import { GrovyProcConfigService } from './grovy-proc-config.service';
import { IApiSvcProcConfig } from 'app/shared/model/api-svc-proc-config.model';
import { ApiSvcProcConfigService } from 'app/entities/api-svc-proc-config';

@Component({
    selector: 'jhi-grovy-proc-config-update',
    templateUrl: './grovy-proc-config-update.component.html'
})
export class GrovyProcConfigUpdateComponent implements OnInit {
    grovyProcConfig: IGrovyProcConfig;
    isSaving: boolean;

    configs: IApiSvcProcConfig[];
    dateCreated: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private grovyProcConfigService: GrovyProcConfigService,
        private apiSvcProcConfigService: ApiSvcProcConfigService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ grovyProcConfig }) => {
            this.grovyProcConfig = grovyProcConfig;
            this.dateCreated = this.grovyProcConfig.dateCreated != null ? this.grovyProcConfig.dateCreated.format(DATE_TIME_FORMAT) : null;
        });
        this.apiSvcProcConfigService.query({ filter: 'grovyprocconfig-is-null' }).subscribe(
            (res: HttpResponse<IApiSvcProcConfig[]>) => {
                if (!this.grovyProcConfig.configId) {
                    this.configs = res.body;
                } else {
                    this.apiSvcProcConfigService.find(this.grovyProcConfig.configId).subscribe(
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
        this.grovyProcConfig.dateCreated = this.dateCreated != null ? moment(this.dateCreated, DATE_TIME_FORMAT) : null;
        if (this.grovyProcConfig.id !== undefined) {
            this.subscribeToSaveResponse(this.grovyProcConfigService.update(this.grovyProcConfig));
        } else {
            this.subscribeToSaveResponse(this.grovyProcConfigService.create(this.grovyProcConfig));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGrovyProcConfig>>) {
        result.subscribe((res: HttpResponse<IGrovyProcConfig>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
