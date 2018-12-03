import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApiPublisherProfile } from 'app/shared/model/api-publisher-profile.model';

type EntityResponseType = HttpResponse<IApiPublisherProfile>;
type EntityArrayResponseType = HttpResponse<IApiPublisherProfile[]>;

@Injectable({ providedIn: 'root' })
export class ApiPublisherProfileService {
    public resourceUrl = SERVER_API_URL + 'api/api-publisher-profiles';

    constructor(private http: HttpClient) {}

    create(apiPublisherProfile: IApiPublisherProfile): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(apiPublisherProfile);
        return this.http
            .post<IApiPublisherProfile>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(apiPublisherProfile: IApiPublisherProfile): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(apiPublisherProfile);
        return this.http
            .put<IApiPublisherProfile>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IApiPublisherProfile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IApiPublisherProfile[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(apiPublisherProfile: IApiPublisherProfile): IApiPublisherProfile {
        const copy: IApiPublisherProfile = Object.assign({}, apiPublisherProfile, {
            dateCreated:
                apiPublisherProfile.dateCreated != null && apiPublisherProfile.dateCreated.isValid()
                    ? apiPublisherProfile.dateCreated.toJSON()
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
            res.body.forEach((apiPublisherProfile: IApiPublisherProfile) => {
                apiPublisherProfile.dateCreated = apiPublisherProfile.dateCreated != null ? moment(apiPublisherProfile.dateCreated) : null;
            });
        }
        return res;
    }
}
