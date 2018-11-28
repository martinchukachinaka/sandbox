import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApicockpitSharedModule } from 'app/shared';
import {
    GrovyProcConfigComponent,
    GrovyProcConfigDetailComponent,
    GrovyProcConfigUpdateComponent,
    GrovyProcConfigDeletePopupComponent,
    GrovyProcConfigDeleteDialogComponent,
    grovyProcConfigRoute,
    grovyProcConfigPopupRoute
} from './';

const ENTITY_STATES = [...grovyProcConfigRoute, ...grovyProcConfigPopupRoute];

@NgModule({
    imports: [ApicockpitSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GrovyProcConfigComponent,
        GrovyProcConfigDetailComponent,
        GrovyProcConfigUpdateComponent,
        GrovyProcConfigDeleteDialogComponent,
        GrovyProcConfigDeletePopupComponent
    ],
    entryComponents: [
        GrovyProcConfigComponent,
        GrovyProcConfigUpdateComponent,
        GrovyProcConfigDeleteDialogComponent,
        GrovyProcConfigDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApicockpitGrovyProcConfigModule {}
