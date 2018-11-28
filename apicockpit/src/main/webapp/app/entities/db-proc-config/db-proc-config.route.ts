import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DbProcConfig } from 'app/shared/model/db-proc-config.model';
import { DbProcConfigService } from './db-proc-config.service';
import { DbProcConfigComponent } from './db-proc-config.component';
import { DbProcConfigDetailComponent } from './db-proc-config-detail.component';
import { DbProcConfigUpdateComponent } from './db-proc-config-update.component';
import { DbProcConfigDeletePopupComponent } from './db-proc-config-delete-dialog.component';
import { IDbProcConfig } from 'app/shared/model/db-proc-config.model';

@Injectable({ providedIn: 'root' })
export class DbProcConfigResolve implements Resolve<IDbProcConfig> {
    constructor(private service: DbProcConfigService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DbProcConfig> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DbProcConfig>) => response.ok),
                map((dbProcConfig: HttpResponse<DbProcConfig>) => dbProcConfig.body)
            );
        }
        return of(new DbProcConfig());
    }
}

export const dbProcConfigRoute: Routes = [
    {
        path: 'db-proc-config',
        component: DbProcConfigComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.dbProcConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'db-proc-config/:id/view',
        component: DbProcConfigDetailComponent,
        resolve: {
            dbProcConfig: DbProcConfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.dbProcConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'db-proc-config/new',
        component: DbProcConfigUpdateComponent,
        resolve: {
            dbProcConfig: DbProcConfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.dbProcConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'db-proc-config/:id/edit',
        component: DbProcConfigUpdateComponent,
        resolve: {
            dbProcConfig: DbProcConfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.dbProcConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dbProcConfigPopupRoute: Routes = [
    {
        path: 'db-proc-config/:id/delete',
        component: DbProcConfigDeletePopupComponent,
        resolve: {
            dbProcConfig: DbProcConfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apicockpitApp.dbProcConfig.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
