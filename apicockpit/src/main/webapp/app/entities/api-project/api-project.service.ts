import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApiProject } from 'app/shared/model/api-project.model';

type EntityResponseType = HttpResponse<IApiProject>;
type EntityArrayResponseType = HttpResponse<IApiProject[]>;

@Injectable({ providedIn: 'root' })
export class ApiProjectService {
    public resourceUrl = SERVER_API_URL + 'api/api-projects';

    constructor(private http: HttpClient) {}

    create(apiProject: IApiProject): Observable<EntityResponseType> {
        return this.http
            .post<IApiProject>(this.resourceUrl, apiProject, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(apiProject: IApiProject): Observable<EntityResponseType> {
        return this.http
            .put<IApiProject>(this.resourceUrl, apiProject, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IApiProject>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IApiProject[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(apiProject: IApiProject): IApiProject {
        const copy: IApiProject = Object.assign({}, apiProject, {
            dateCreated: apiProject.dateCreated != null && apiProject.dateCreated.isValid() ? apiProject.dateCreated.toJSON() : null
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
            res.body.forEach((apiProject: IApiProject) => {
                apiProject.dateCreated = apiProject.dateCreated != null ? moment(apiProject.dateCreated) : null;
            });
        }
        return res;
    }
}
