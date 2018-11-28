import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApiProjectService } from 'app/shared/model/api-project-service.model';
import { ApiProjectServiceService } from './api-project-service.service';
import { ApiProjectServiceComponent } from './api-project-service.component';
import { ApiProjectServiceDetailComponent } from './api-project-service-detail.component';
import { ApiProjectServiceUpdateComponent } from './api-project-service-update.component';
import { ApiProjectServiceDeletePopupComponent } from './api-project-service-delete-dialog.component';
import { IApiProjectService } from 'app/shared/model/api-project-service.model';

@Injectable({ providedIn: 'root' })
export class ApiProjectServiceResolve implements Resolve<IApiProjectService> {
    constructor(private service: ApiProjectServiceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ApiProjectService> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ApiProjectService>) => response.ok),
                map((apiProjectService: HttpResponse<ApiProjectService>) => apiProjectService.body)
            );
        }
        return of(new ApiProjectService());
    }
}

export const apiProjectServiceRoute: Routes = [
    {
        path: 'api-project-service',
        component: ApiProjectServiceComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'apicockpitApp.apiProjectService.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-project-service/:id/view',
        component: ApiProjectServiceDetailComponent,
        resolve: {
            apiProjectService: ApiProjectServiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiProjectService.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-project-service/new',
        component: ApiProjectServiceUpdateComponent,
        resolve: {
            apiProjectService: ApiProjectServiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiProjectService.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-project-service/:id/edit',
        component: ApiProjectServiceUpdateComponent,
        resolve: {
            apiProjectService: ApiProjectServiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiProjectService.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const apiProjectServicePopupRoute: Routes = [
    {
        path: 'api-project-service/:id/delete',
        component: ApiProjectServiceDeletePopupComponent,
        resolve: {
            apiProjectService: ApiProjectServiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiProjectService.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
