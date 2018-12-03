import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGrovyProcConfig } from 'app/shared/model/grovy-proc-config.model';

type EntityResponseType = HttpResponse<IGrovyProcConfig>;
type EntityArrayResponseType = HttpResponse<IGrovyProcConfig[]>;

@Injectable({ providedIn: 'root' })
export class GrovyProcConfigService {
    public resourceUrl = SERVER_API_URL + 'api/grovy-proc-configs';

    constructor(private http: HttpClient) {}

    create(grovyProcConfig: IGrovyProcConfig): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(grovyProcConfig);
        return this.http
            .post<IGrovyProcConfig>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(grovyProcConfig: IGrovyProcConfig): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(grovyProcConfig);
        return this.http
            .put<IGrovyProcConfig>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IGrovyProcConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IGrovyProcConfig[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(grovyProcConfig: IGrovyProcConfig): IGrovyProcConfig {
        const copy: IGrovyProcConfig = Object.assign({}, grovyProcConfig, {
            dateCreated:
                grovyProcConfig.dateCreated != null && grovyProcConfig.dateCreated.isValid() ? grovyProcConfig.dateCreated.toJSON() : null
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
            res.body.forEach((grovyProcConfig: IGrovyProcConfig) => {
                grovyProcConfig.dateCreated = grovyProcConfig.dateCreated != null ? moment(grovyProcConfig.dateCreated) : null;
            });
        }
        return res;
    }
}
