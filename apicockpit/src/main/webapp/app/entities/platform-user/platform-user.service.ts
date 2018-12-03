import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPlatformUser } from 'app/shared/model/platform-user.model';

type EntityResponseType = HttpResponse<IPlatformUser>;
type EntityArrayResponseType = HttpResponse<IPlatformUser[]>;

@Injectable({ providedIn: 'root' })
export class PlatformUserService {
    public resourceUrl = SERVER_API_URL + 'api/platform-users';

    constructor(private http: HttpClient) {}

    create(platformUser: IPlatformUser): Observable<EntityResponseType> {
        return this.http.post<IPlatformUser>(this.resourceUrl, platformUser, { observe: 'response' });
    }

    update(platformUser: IPlatformUser): Observable<EntityResponseType> {
        return this.http.put<IPlatformUser>(this.resourceUrl, platformUser, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPlatformUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPlatformUser[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
