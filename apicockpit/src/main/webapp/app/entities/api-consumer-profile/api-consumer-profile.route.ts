import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApiConsumerProfile } from 'app/shared/model/api-consumer-profile.model';
import { ApiConsumerProfileService } from './api-consumer-profile.service';
import { ApiConsumerProfileComponent } from './api-consumer-profile.component';
import { ApiConsumerProfileDetailComponent } from './api-consumer-profile-detail.component';
import { ApiConsumerProfileUpdateComponent } from './api-consumer-profile-update.component';
import { ApiConsumerProfileDeletePopupComponent } from './api-consumer-profile-delete-dialog.component';
import { IApiConsumerProfile } from 'app/shared/model/api-consumer-profile.model';

@Injectable({ providedIn: 'root' })
export class ApiConsumerProfileResolve implements Resolve<IApiConsumerProfile> {
    constructor(private service: ApiConsumerProfileService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ApiConsumerProfile> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ApiConsumerProfile>) => response.ok),
                map((apiConsumerProfile: HttpResponse<ApiConsumerProfile>) => apiConsumerProfile.body)
            );
        }
        return of(new ApiConsumerProfile());
    }
}

export const apiConsumerProfileRoute: Routes = [
    {
        path: 'api-consumer-profile',
        component: ApiConsumerProfileComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'apicockpitApp.apiConsumerProfile.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-consumer-profile/:id/view',
        component: ApiConsumerProfileDetailComponent,
        resolve: {
            apiConsumerProfile: ApiConsumerProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiConsumerProfile.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-consumer-profile/new',
        component: ApiConsumerProfileUpdateComponent,
        resolve: {
            apiConsumerProfile: ApiConsumerProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiConsumerProfile.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-consumer-profile/:id/edit',
        component: ApiConsumerProfileUpdateComponent,
        resolve: {
            apiConsumerProfile: ApiConsumerProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiConsumerProfile.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const apiConsumerProfilePopupRoute: Routes = [
    {
        path: 'api-consumer-profile/:id/delete',
        component: ApiConsumerProfileDeletePopupComponent,
        resolve: {
            apiConsumerProfile: ApiConsumerProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiConsumerProfile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
