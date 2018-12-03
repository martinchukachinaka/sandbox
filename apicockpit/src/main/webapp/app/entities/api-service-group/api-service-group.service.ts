import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApiServiceGroup } from 'app/shared/model/api-service-group.model';

type EntityResponseType = HttpResponse<IApiServiceGroup>;
type EntityArrayResponseType = HttpResponse<IApiServiceGroup[]>;

@Injectable({ providedIn: 'root' })
export class ApiServiceGroupService {
    public resourceUrl = SERVER_API_URL + 'api/api-service-groups';

    constructor(private http: HttpClient) {}

    create(apiServiceGroup: IApiServiceGroup): Observable<EntityResponseType> {
        return this.http.post<IApiServiceGroup>(this.resourceUrl, apiServiceGroup, { observe: 'response' });
    }

    update(apiServiceGroup: IApiServiceGroup): Observable<EntityResponseType> {
        return this.http.put<IApiServiceGroup>(this.resourceUrl, apiServiceGroup, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IApiServiceGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IApiServiceGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
