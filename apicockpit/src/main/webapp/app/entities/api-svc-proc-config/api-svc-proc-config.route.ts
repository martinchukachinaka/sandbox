import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApiSvcProcConfig } from 'app/shared/model/api-svc-proc-config.model';
import { ApiSvcProcConfigService } from './api-svc-proc-config.service';
import { ApiSvcProcConfigComponent } from './api-svc-proc-config.component';
import { ApiSvcProcConfigDetailComponent } from './api-svc-proc-config-detail.component';
import { ApiSvcProcConfigUpdateComponent } from './api-svc-proc-config-update.component';
import { ApiSvcProcConfigDeletePopupComponent } from './api-svc-proc-config-delete-dialog.component';
import { IApiSvcProcConfig } from 'app/shared/model/api-svc-proc-config.model';

@Injectable({ providedIn: 'root' })
export class ApiSvcProcConfigResolve implements Resolve<IApiSvcProcConfig> {
    constructor(private service: ApiSvcProcConfigService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ApiSvcProcConfig> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ApiSvcProcConfig>) => response.ok),
                map((apiSvcProcConfig: HttpResponse<ApiSvcProcConfig>) => apiSvcProcConfig.body)
            );
        }
        return of(new ApiSvcProcConfig());
    }
}

export const apiSvcProcConfigRoute: Routes = [
    {
        path: 'api-svc-proc-config',
        component: ApiSvcProcConfigComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiSvcProcConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-svc-proc-config/:id/view',
        component: ApiSvcProcConfigDetailComponent,
        resolve: {
            apiSvcProcConfig: ApiSvcProcConfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiSvcProcConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-svc-proc-config/new',
        component: ApiSvcProcConfigUpdateComponent,
        resolve: {
            apiSvcProcConfig: ApiSvcProcConfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiSvcProcConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-svc-proc-config/:id/edit',
        component: ApiSvcProcConfigUpdateComponent,
        resolve: {
            apiSvcProcConfig: ApiSvcProcConfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiSvcProcConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const apiSvcProcConfigPopupRoute: Routes = [
    {
        path: 'api-svc-proc-config/:id/delete',
        component: ApiSvcProcConfigDeletePopupComponent,
        resolve: {
            apiSvcProcConfig: ApiSvcProcConfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiSvcProcConfig.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
