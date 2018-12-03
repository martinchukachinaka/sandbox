import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApicockpitSharedModule } from 'app/shared';
import {
    ApiProjectAuthConfigComponent,
    ApiProjectAuthConfigDetailComponent,
    ApiProjectAuthConfigUpdateComponent,
    ApiProjectAuthConfigDeletePopupComponent,
    ApiProjectAuthConfigDeleteDialogComponent,
    apiProjectAuthConfigRoute,
    apiProjectAuthConfigPopupRoute
} from './';

const ENTITY_STATES = [...apiProjectAuthConfigRoute, ...apiProjectAuthConfigPopupRoute];

@NgModule({
    imports: [ApicockpitSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ApiProjectAuthConfigComponent,
        ApiProjectAuthConfigDetailComponent,
        ApiProjectAuthConfigUpdateComponent,
        ApiProjectAuthConfigDeleteDialogComponent,
        ApiProjectAuthConfigDeletePopupComponent
    ],
    entryComponents: [
        ApiProjectAuthConfigComponent,
        ApiProjectAuthConfigUpdateComponent,
        ApiProjectAuthConfigDeleteDialogComponent,
        ApiProjectAuthConfigDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApicockpitApiProjectAuthConfigModule {}
