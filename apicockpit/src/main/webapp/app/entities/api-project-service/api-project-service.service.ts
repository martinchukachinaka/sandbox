import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApiProjectService } from 'app/shared/model/api-project-service.model';

type EntityResponseType = HttpResponse<IApiProjectService>;
type EntityArrayResponseType = HttpResponse<IApiProjectService[]>;

@Injectable({ providedIn: 'root' })
export class ApiProjectServiceService {
    public resourceUrl = SERVER_API_URL + 'api/api-project-services';

    constructor(private http: HttpClient) {}

    create(apiProjectService: IApiProjectService): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(apiProjectService);
        return this.http
            .post<IApiProjectService>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(apiProjectService: IApiProjectService): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(apiProjectService);
        return this.http
            .put<IApiProjectService>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IApiProjectService>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IApiProjectService[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(apiProjectService: IApiProjectService): IApiProjectService {
        const copy: IApiProjectService = Object.assign({}, apiProjectService, {
            dateCreated:
                apiProjectService.dateCreated != null && apiProjectService.dateCreated.isValid()
                    ? apiProjectService.dateCreated.toJSON()
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
            res.body.forEach((apiProjectService: IApiProjectService) => {
                apiProjectService.dateCreated = apiProjectService.dateCreated != null ? moment(apiProjectService.dateCreated) : null;
            });
        }
        return res;
    }
}
