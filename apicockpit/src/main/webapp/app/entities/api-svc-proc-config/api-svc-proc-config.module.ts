import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApicockpitSharedModule } from 'app/shared';
import {
    ApiSvcProcConfigComponent,
    ApiSvcProcConfigDetailComponent,
    ApiSvcProcConfigUpdateComponent,
    ApiSvcProcConfigDeletePopupComponent,
    ApiSvcProcConfigDeleteDialogComponent,
    apiSvcProcConfigRoute,
    apiSvcProcConfigPopupRoute
} from './';

const ENTITY_STATES = [...apiSvcProcConfigRoute, ...apiSvcProcConfigPopupRoute];

@NgModule({
    imports: [ApicockpitSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ApiSvcProcConfigComponent,
        ApiSvcProcConfigDetailComponent,
        ApiSvcProcConfigUpdateComponent,
        ApiSvcProcConfigDeleteDialogComponent,
        ApiSvcProcConfigDeletePopupComponent
    ],
    entryComponents: [
        ApiSvcProcConfigComponent,
        ApiSvcProcConfigUpdateComponent,
        ApiSvcProcConfigDeleteDialogComponent,
        ApiSvcProcConfigDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApicockpitApiSvcProcConfigModule {}
