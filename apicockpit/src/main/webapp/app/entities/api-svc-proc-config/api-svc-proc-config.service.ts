import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApiSvcProcConfig } from 'app/shared/model/api-svc-proc-config.model';

type EntityResponseType = HttpResponse<IApiSvcProcConfig>;
type EntityArrayResponseType = HttpResponse<IApiSvcProcConfig[]>;

@Injectable({ providedIn: 'root' })
export class ApiSvcProcConfigService {
    public resourceUrl = SERVER_API_URL + 'api/api-svc-proc-configs';

    constructor(private http: HttpClient) {}

    create(apiSvcProcConfig: IApiSvcProcConfig): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(apiSvcProcConfig);
        return this.http
            .post<IApiSvcProcConfig>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(apiSvcProcConfig: IApiSvcProcConfig): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(apiSvcProcConfig);
        return this.http
            .put<IApiSvcProcConfig>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IApiSvcProcConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IApiSvcProcConfig[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(apiSvcProcConfig: IApiSvcProcConfig): IApiSvcProcConfig {
        const copy: IApiSvcProcConfig = Object.assign({}, apiSvcProcConfig, {
            dateCreated:
                apiSvcProcConfig.dateCreated != null && apiSvcProcConfig.dateCreated.isValid()
                    ? apiSvcProcConfig.dateCreated.toJSON()
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateCreated = res.body.dateCreated != null ? moment(res.body.dateCreated) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((apiSvcProcConfig: IApiSvcProcConfig) => {
                apiSvcProcConfig.dateCreated = apiSvcProcConfig.dateCreated != null ? moment(apiSvcProcConfig.dateCreated) : null;
            });
        }
        return res;
    }
}
