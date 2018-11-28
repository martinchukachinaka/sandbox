import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApiProjectAuthConfig } from 'app/shared/model/api-project-auth-config.model';

type EntityResponseType = HttpResponse<IApiProjectAuthConfig>;
type EntityArrayResponseType = HttpResponse<IApiProjectAuthConfig[]>;

@Injectable({ providedIn: 'root' })
export class ApiProjectAuthConfigService {
    public resourceUrl = SERVER_API_URL + 'api/api-project-auth-configs';

    constructor(private http: HttpClient) {}

    create(apiProjectAuthConfig: IApiProjectAuthConfig): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(apiProjectAuthConfig);
        return this.http
            .post<IApiProjectAuthConfig>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(apiProjectAuthConfig: IApiProjectAuthConfig): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(apiProjectAuthConfig);
        return this.http
            .put<IApiProjectAuthConfig>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IApiProjectAuthConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IApiProjectAuthConfig[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(apiProjectAuthConfig: IApiProjectAuthConfig): IApiProjectAuthConfig {
        const copy: IApiProjectAuthConfig = Object.assign({}, apiProjectAuthConfig, {
            dateCreated:
                apiProjectAuthConfig.dateCreated != null && apiProjectAuthConfig.dateCreated.isValid()
                    ? apiProjectAuthConfig.dateCreated.toJSON()
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
            res.body.forEach((apiProjectAuthConfig: IApiProjectAuthConfig) => {
                apiProjectAuthConfig.dateCreated =
                    apiProjectAuthConfig.dateCreated != null ? moment(apiProjectAuthConfig.dateCreated) : null;
            });
        }
        return res;
    }
}
