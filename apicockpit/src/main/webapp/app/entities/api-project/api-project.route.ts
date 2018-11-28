import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApiProject } from 'app/shared/model/api-project.model';
import { ApiProjectService } from './api-project.service';
import { ApiProjectComponent } from './api-project.component';
import { ApiProjectDetailComponent } from './api-project-detail.component';
import { ApiProjectUpdateComponent } from './api-project-update.component';
import { ApiProjectDeletePopupComponent } from './api-project-delete-dialog.component';
import { IApiProject } from 'app/shared/model/api-project.model';

@Injectable({ providedIn: 'root' })
export class ApiProjectResolve implements Resolve<IApiProject> {
    constructor(private service: ApiProjectService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ApiProject> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ApiProject>) => response.ok),
                map((apiProject: HttpResponse<ApiProject>) => apiProject.body)
            );
        }
        return of(new ApiProject());
    }
}

export const apiProjectRoute: Routes = [
    {
        path: 'api-project',
        component: ApiProjectComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            // authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'apicockpitApp.apiProject.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-project/:id/view',
        component: ApiProjectDetailComponent,
        resolve: {
            apiProject: ApiProjectResolve
        },
        data: {
            // authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiProject.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-project/new',
        component: ApiProjectUpdateComponent,
        resolve: {
            apiProject: ApiProjectResolve
        },
        data: {
            // authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiProject.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'api-project/:id/edit',
        component: ApiProjectUpdateComponent,
        resolve: {
            apiProject: ApiProjectResolve
        },
        data: {
            // authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiProject.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const apiProjectPopupRoute: Routes = [
    {
        path: 'api-project/:id/delete',
        component: ApiProjectDeletePopupComponent,
        resolve: {
            apiProject: ApiProjectResolve
        },
        data: {
            // authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.apiProject.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
