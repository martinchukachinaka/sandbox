import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApicockpitSharedModule } from 'app/shared';
import {
    ApiServiceGroupComponent,
    ApiServiceGroupDetailComponent,
    ApiServiceGroupUpdateComponent,
    ApiServiceGroupDeletePopupComponent,
    ApiServiceGroupDeleteDialogComponent,
    apiServiceGroupRoute,
    apiServiceGroupPopupRoute
} from './';

const ENTITY_STATES = [...apiServiceGroupRoute, ...apiServiceGroupPopupRoute];

@NgModule({
    imports: [ApicockpitSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ApiServiceGroupComponent,
        ApiServiceGroupDetailComponent,
        ApiServiceGroupUpdateComponent,
        ApiServiceGroupDeleteDialogComponent,
        ApiServiceGroupDeletePopupComponent
    ],
    entryComponents: [
        ApiServiceGroupComponent,
        ApiServiceGroupUpdateComponent,
        ApiServiceGroupDeleteDialogComponent,
        ApiServiceGroupDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApicockpitApiServiceGroupModule {}
