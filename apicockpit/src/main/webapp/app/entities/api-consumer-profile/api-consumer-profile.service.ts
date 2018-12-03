import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApiConsumerProfile } from 'app/shared/model/api-consumer-profile.model';

type EntityResponseType = HttpResponse<IApiConsumerProfile>;
type EntityArrayResponseType = HttpResponse<IApiConsumerProfile[]>;

@Injectable({ providedIn: 'root' })
export class ApiConsumerProfileService {
    public resourceUrl = SERVER_API_URL + 'api/api-consumer-profiles';

    constructor(private http: HttpClient) {}

    create(apiConsumerProfile: IApiConsumerProfile): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(apiConsumerProfile);
        return this.http
            .post<IApiConsumerProfile>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(apiConsumerProfile: IApiConsumerProfile): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(apiConsumerProfile);
        return this.http
            .put<IApiConsumerProfile>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IApiConsumerProfile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IApiConsumerProfile[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(apiConsumerProfile: IApiConsumerProfile): IApiConsumerProfile {
        const copy: IApiConsumerProfile = Object.assign({}, apiConsumerProfile, {
            dateCreated:
                apiConsumerProfile.dateCreated != null && apiConsumerProfile.dateCreated.isValid()
                    ? apiConsumerProfile.dateCreated.toJSON()
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
            res.body.forEach((apiConsumerProfile: IApiConsumerProfile) => {
                apiConsumerProfile.dateCreated = apiConsumerProfile.dateCreated != null ? moment(apiConsumerProfile.dateCreated) : null;
            });
        }
        return res;
    }
}
