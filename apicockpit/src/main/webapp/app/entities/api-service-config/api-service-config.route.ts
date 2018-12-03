import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApiServiceConfig } from 'app/shared/model/api-service-config.model';
import { ApiServiceConfigService } from './api-service-config.service';
import { ApiServiceConfigComponent } from './api-service-config.component';
import { ApiServiceConfigDetailComponent } from './api-service-config-detail.component';
import { ApiServiceConfigUpdateComponent } from './api-service-config-update.component';
import { ApiServiceConfigDeletePopupComponent } from './api-service-config-delete-dialog.component';
import { IApiServiceConfig } from 'app/shared/model/api-service-config.model';

@Injectable({ providedIn: 'root' })
export class ApiServiceConfigResolve implements Resolve<IApiServiceConfig> {
    constructor(private service: ApiServiceConfigService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ApiServiceConfig> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ApiServiceConfig>) => response.ok),
                map((apiServiceConfig: HttpResponse<ApiServiceConfig>) => apiServiceConfig.body)
            );
        }
        return of(new ApiServiceConfig());
    }
}

export const apiServiceConfigRoute: Routes = [
    {
        path: 'api-service-config',
        component: ApiServiceConfigComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'apicockpitApp.apiServiceConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-service-config/:id/view',
        component: ApiServiceConfigDetailComponent,
        resolve: {
            apiServiceConfig: ApiServiceConfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiServiceConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-service-config/new',
        component: ApiServiceConfigUpdateComponent,
        resolve: {
            apiServiceConfig: ApiServiceConfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiServiceConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-service-config/:id/edit',
        component: ApiServiceConfigUpdateComponent,
        resolve: {
            apiServiceConfig: ApiServiceConfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiServiceConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const apiServiceConfigPopupRoute: Routes = [
    {
        path: 'api-service-config/:id/delete',
        component: ApiServiceConfigDeletePopupComponent,
        resolve: {
            apiServiceConfig: ApiServiceConfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiServiceConfig.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
