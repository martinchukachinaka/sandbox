import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApiPublisherProfile } from 'app/shared/model/api-publisher-profile.model';
import { ApiPublisherProfileService } from './api-publisher-profile.service';
import { ApiPublisherProfileComponent } from './api-publisher-profile.component';
import { ApiPublisherProfileDetailComponent } from './api-publisher-profile-detail.component';
import { ApiPublisherProfileUpdateComponent } from './api-publisher-profile-update.component';
import { ApiPublisherProfileDeletePopupComponent } from './api-publisher-profile-delete-dialog.component';
import { IApiPublisherProfile } from 'app/shared/model/api-publisher-profile.model';

@Injectable({ providedIn: 'root' })
export class ApiPublisherProfileResolve implements Resolve<IApiPublisherProfile> {
    constructor(private service: ApiPublisherProfileService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ApiPublisherProfile> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ApiPublisherProfile>) => response.ok),
                map((apiPublisherProfile: HttpResponse<ApiPublisherProfile>) => apiPublisherProfile.body)
            );
        }
        return of(new ApiPublisherProfile());
    }
}

export const apiPublisherProfileRoute: Routes = [
    {
        path: 'api-publisher-profile',
        component: ApiPublisherProfileComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'apicockpitApp.apiPublisherProfile.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-publisher-profile/:id/view',
        component: ApiPublisherProfileDetailComponent,
        resolve: {
            apiPublisherProfile: ApiPublisherProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiPublisherProfile.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-publisher-profile/new',
        component: ApiPublisherProfileUpdateComponent,
        resolve: {
            apiPublisherProfile: ApiPublisherProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiPublisherProfile.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-publisher-profile/:id/edit',
        component: ApiPublisherProfileUpdateComponent,
        resolve: {
            apiPublisherProfile: ApiPublisherProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiPublisherProfile.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const apiPublisherProfilePopupRoute: Routes = [
    {
        path: 'api-publisher-profile/:id/delete',
        component: ApiPublisherProfileDeletePopupComponent,
        resolve: {
            apiPublisherProfile: ApiPublisherProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiPublisherProfile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
