import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDbProcConfig } from 'app/shared/model/db-proc-config.model';

type EntityResponseType = HttpResponse<IDbProcConfig>;
type EntityArrayResponseType = HttpResponse<IDbProcConfig[]>;

@Injectable({ providedIn: 'root' })
export class DbProcConfigService {
    public resourceUrl = SERVER_API_URL + 'api/db-proc-configs';

    constructor(private http: HttpClient) {}

    create(dbProcConfig: IDbProcConfig): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dbProcConfig);
        return this.http
            .post<IDbProcConfig>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(dbProcConfig: IDbProcConfig): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dbProcConfig);
        return this.http
            .put<IDbProcConfig>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDbProcConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDbProcConfig[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(dbProcConfig: IDbProcConfig): IDbProcConfig {
        const copy: IDbProcConfig = Object.assign({}, dbProcConfig, {
            dateCreated: dbProcConfig.dateCreated != null && dbProcConfig.dateCreated.isValid() ? dbProcConfig.dateCreated.toJSON() : null
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
            res.body.forEach((dbProcConfig: IDbProcConfig) => {
                dbProcConfig.dateCreated = dbProcConfig.dateCreated != null ? moment(dbProcConfig.dateCreated) : null;
            });
        }
        return res;
    }
}
