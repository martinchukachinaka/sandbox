import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApicockpitSharedModule } from 'app/shared';
import {
    ApiProjectServiceComponent,
    ApiProjectServiceDetailComponent,
    ApiProjectServiceUpdateComponent,
    ApiProjectServiceDeletePopupComponent,
    ApiProjectServiceDeleteDialogComponent,
    apiProjectServiceRoute,
    apiProjectServicePopupRoute
} from './';

const ENTITY_STATES = [...apiProjectServiceRoute, ...apiProjectServicePopupRoute];

@NgModule({
    imports: [ApicockpitSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ApiProjectServiceComponent,
        ApiProjectServiceDetailComponent,
        ApiProjectServiceUpdateComponent,
        ApiProjectServiceDeleteDialogComponent,
        ApiProjectServiceDeletePopupComponent
    ],
    entryComponents: [
        ApiProjectServiceComponent,
        ApiProjectServiceUpdateComponent,
        ApiProjectServiceDeleteDialogComponent,
        ApiProjectServiceDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApicockpitApiProjectServiceModule {}
