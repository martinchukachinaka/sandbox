import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PlatformUser } from 'app/shared/model/platform-user.model';
import { PlatformUserService } from './platform-user.service';
import { PlatformUserComponent } from './platform-user.component';
import { PlatformUserDetailComponent } from './platform-user-detail.component';
import { PlatformUserUpdateComponent } from './platform-user-update.component';
import { PlatformUserDeletePopupComponent } from './platform-user-delete-dialog.component';
import { IPlatformUser } from 'app/shared/model/platform-user.model';

@Injectable({ providedIn: 'root' })
export class PlatformUserResolve implements Resolve<IPlatformUser> {
    constructor(private service: PlatformUserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<PlatformUser> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PlatformUser>) => response.ok),
                map((platformUser: HttpResponse<PlatformUser>) => platformUser.body)
            );
        }
        return of(new PlatformUser());
    }
}

export const platformUserRoute: Routes = [
    {
        path: 'platform-user',
        component: PlatformUserComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.platformUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'platform-user/:id/view',
        component: PlatformUserDetailComponent,
        resolve: {
            platformUser: PlatformUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.platformUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'platform-user/new',
        component: PlatformUserUpdateComponent,
        resolve: {
            platformUser: PlatformUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.platformUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'platform-user/:id/edit',
        component: PlatformUserUpdateComponent,
        resolve: {
            platformUser: PlatformUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.platformUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const platformUserPopupRoute: Routes = [
    {
        path: 'platform-user/:id/delete',
        component: PlatformUserDeletePopupComponent,
        resolve: {
            platformUser: PlatformUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.platformUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
