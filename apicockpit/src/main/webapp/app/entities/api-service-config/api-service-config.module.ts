import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApicockpitSharedModule } from 'app/shared';
import {
    ApiServiceConfigComponent,
    ApiServiceConfigDetailComponent,
    ApiServiceConfigUpdateComponent,
    ApiServiceConfigDeletePopupComponent,
    ApiServiceConfigDeleteDialogComponent,
    apiServiceConfigRoute,
    apiServiceConfigPopupRoute
} from './';

const ENTITY_STATES = [...apiServiceConfigRoute, ...apiServiceConfigPopupRoute];

@NgModule({
    imports: [ApicockpitSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ApiServiceConfigComponent,
        ApiServiceConfigDetailComponent,
        ApiServiceConfigUpdateComponent,
        ApiServiceConfigDeleteDialogComponent,
        ApiServiceConfigDeletePopupComponent
    ],
    entryComponents: [
        ApiServiceConfigComponent,
        ApiServiceConfigUpdateComponent,
        ApiServiceConfigDeleteDialogComponent,
        ApiServiceConfigDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApicockpitApiServiceConfigModule {}
