import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApiProjectAuthConfig } from 'app/shared/model/api-project-auth-config.model';
import { ApiProjectAuthConfigService } from './api-project-auth-config.service';
import { ApiProjectAuthConfigComponent } from './api-project-auth-config.component';
import { ApiProjectAuthConfigDetailComponent } from './api-project-auth-config-detail.component';
import { ApiProjectAuthConfigUpdateComponent } from './api-project-auth-config-update.component';
import { ApiProjectAuthConfigDeletePopupComponent } from './api-project-auth-config-delete-dialog.component';
import { IApiProjectAuthConfig } from 'app/shared/model/api-project-auth-config.model';

@Injectable({ providedIn: 'root' })
export class ApiProjectAuthConfigResolve implements Resolve<IApiProjectAuthConfig> {
    constructor(private service: ApiProjectAuthConfigService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ApiProjectAuthConfig> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ApiProjectAuthConfig>) => response.ok),
                map((apiProjectAuthConfig: HttpResponse<ApiProjectAuthConfig>) => apiProjectAuthConfig.body)
            );
        }
        return of(new ApiProjectAuthConfig());
    }
}

export const apiProjectAuthConfigRoute: Routes = [
    {
        path: 'api-project-auth-config',
        component: ApiProjectAuthConfigComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'apicockpitApp.apiProjectAuthConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-project-auth-config/:id/view',
        component: ApiProjectAuthConfigDetailComponent,
        resolve: {
            apiProjectAuthConfig: ApiProjectAuthConfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiProjectAuthConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-project-auth-config/new',
        component: ApiProjectAuthConfigUpdateComponent,
        resolve: {
            apiProjectAuthConfig: ApiProjectAuthConfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiProjectAuthConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-project-auth-config/:id/edit',
        component: ApiProjectAuthConfigUpdateComponent,
        resolve: {
            apiProjectAuthConfig: ApiProjectAuthConfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiProjectAuthConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const apiProjectAuthConfigPopupRoute: Routes = [
    {
        path: 'api-project-auth-config/:id/delete',
        component: ApiProjectAuthConfigDeletePopupComponent,
        resolve: {
            apiProjectAuthConfig: ApiProjectAuthConfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiProjectAuthConfig.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
