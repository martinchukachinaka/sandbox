import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApiServiceGroup } from 'app/shared/model/api-service-group.model';
import { ApiServiceGroupService } from './api-service-group.service';
import { ApiServiceGroupComponent } from './api-service-group.component';
import { ApiServiceGroupDetailComponent } from './api-service-group-detail.component';
import { ApiServiceGroupUpdateComponent } from './api-service-group-update.component';
import { ApiServiceGroupDeletePopupComponent } from './api-service-group-delete-dialog.component';
import { IApiServiceGroup } from 'app/shared/model/api-service-group.model';

@Injectable({ providedIn: 'root' })
export class ApiServiceGroupResolve implements Resolve<IApiServiceGroup> {
    constructor(private service: ApiServiceGroupService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ApiServiceGroup> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ApiServiceGroup>) => response.ok),
                map((apiServiceGroup: HttpResponse<ApiServiceGroup>) => apiServiceGroup.body)
            );
        }
        return of(new ApiServiceGroup());
    }
}

export const apiServiceGroupRoute: Routes = [
    {
        path: 'api-service-group',
        component: ApiServiceGroupComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'apicockpitApp.apiServiceGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-service-group/:id/view',
        component: ApiServiceGroupDetailComponent,
        resolve: {
            apiServiceGroup: ApiServiceGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiServiceGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-service-group/new',
        component: ApiServiceGroupUpdateComponent,
        resolve: {
            apiServiceGroup: ApiServiceGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiServiceGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-service-group/:id/edit',
        component: ApiServiceGroupUpdateComponent,
        resolve: {
            apiServiceGroup: ApiServiceGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiServiceGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const apiServiceGroupPopupRoute: Routes = [
    {
        path: 'api-service-group/:id/delete',
        component: ApiServiceGroupDeletePopupComponent,
        resolve: {
            apiServiceGroup: ApiServiceGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiServiceGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
