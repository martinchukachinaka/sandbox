import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { GrovyProcConfig } from 'app/shared/model/grovy-proc-config.model';
import { GrovyProcConfigService } from './grovy-proc-config.service';
import { GrovyProcConfigComponent } from './grovy-proc-config.component';
import { GrovyProcConfigDetailComponent } from './grovy-proc-config-detail.component';
import { GrovyProcConfigUpdateComponent } from './grovy-proc-config-update.component';
import { GrovyProcConfigDeletePopupComponent } from './grovy-proc-config-delete-dialog.component';
import { IGrovyProcConfig } from 'app/shared/model/grovy-proc-config.model';

@Injectable({ providedIn: 'root' })
export class GrovyProcConfigResolve implements Resolve<IGrovyProcConfig> {
    constructor(private service: GrovyProcConfigService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<GrovyProcConfig> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<GrovyProcConfig>) => response.ok),
                map((grovyProcConfig: HttpResponse<GrovyProcConfig>) => grovyProcConfig.body)
            );
        }
        return of(new GrovyProcConfig());
    }
}

export const grovyProcConfigRoute: Routes = [
    {
        path: 'grovy-proc-config',
        component: GrovyProcConfigComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.grovyProcConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'grovy-proc-config/:id/view',
        component: GrovyProcConfigDetailComponent,
        resolve: {
            grovyProcConfig: GrovyProcConfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.grovyProcConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'grovy-proc-config/new',
        component: GrovyProcConfigUpdateComponent,
        resolve: {
            grovyProcConfig: GrovyProcConfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.grovyProcConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'grovy-proc-config/:id/edit',
        component: GrovyProcConfigUpdateComponent,
        resolve: {
            grovyProcConfig: GrovyProcConfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.grovyProcConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const grovyProcConfigPopupRoute: Routes = [
    {
        path: 'grovy-proc-config/:id/delete',
        component: GrovyProcConfigDeletePopupComponent,
        resolve: {
            grovyProcConfig: GrovyProcConfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.grovyProcConfig.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
