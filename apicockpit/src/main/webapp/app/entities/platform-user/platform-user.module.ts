import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApicockpitSharedModule } from 'app/shared';
import { ApicockpitAdminModule } from 'app/admin/admin.module';
import {
    PlatformUserComponent,
    PlatformUserDetailComponent,
    PlatformUserUpdateComponent,
    PlatformUserDeletePopupComponent,
    PlatformUserDeleteDialogComponent,
    platformUserRoute,
    platformUserPopupRoute
} from './';

const ENTITY_STATES = [...platformUserRoute, ...platformUserPopupRoute];

@NgModule({
    imports: [ApicockpitSharedModule, ApicockpitAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PlatformUserComponent,
        PlatformUserDetailComponent,
        PlatformUserUpdateComponent,
        PlatformUserDeleteDialogComponent,
        PlatformUserDeletePopupComponent
    ],
    entryComponents: [
        PlatformUserComponent,
        PlatformUserUpdateComponent,
        PlatformUserDeleteDialogComponent,
        PlatformUserDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApicockpitPlatformUserModule {}
