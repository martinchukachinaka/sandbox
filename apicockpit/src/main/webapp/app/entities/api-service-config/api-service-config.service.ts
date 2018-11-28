import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApiServiceConfig } from 'app/shared/model/api-service-config.model';

type EntityResponseType = HttpResponse<IApiServiceConfig>;
type EntityArrayResponseType = HttpResponse<IApiServiceConfig[]>;

@Injectable({ providedIn: 'root' })
export class ApiServiceConfigService {
    public resourceUrl = SERVER_API_URL + 'api/api-service-configs';

    constructor(private http: HttpClient) {}

    create(apiServiceConfig: IApiServiceConfig): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(apiServiceConfig);
        return this.http
            .post<IApiServiceConfig>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(apiServiceConfig: IApiServiceConfig): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(apiServiceConfig);
        return this.http
            .put<IApiServiceConfig>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IApiServiceConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IApiServiceConfig[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(apiServiceConfig: IApiServiceConfig): IApiServiceConfig {
        const copy: IApiServiceConfig = Object.assign({}, apiServiceConfig, {
            dateCreated:
                apiServiceConfig.dateCreated != null && apiServiceConfig.dateCreated.isValid()
                    ? apiServiceConfig.dateCreated.toJSON()
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
            res.body.forEach((apiServiceConfig: IApiServiceConfig) => {
                apiServiceConfig.dateCreated = apiServiceConfig.dateCreated != null ? moment(apiServiceConfig.dateCreated) : null;
            });
        }
        return res;
    }
}
