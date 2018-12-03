import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApicockpitSharedModule } from 'app/shared';
import {
    DbProcConfigComponent,
    DbProcConfigDetailComponent,
    DbProcConfigUpdateComponent,
    DbProcConfigDeletePopupComponent,
    DbProcConfigDeleteDialogComponent,
    dbProcConfigRoute,
    dbProcConfigPopupRoute
} from './';

const ENTITY_STATES = [...dbProcConfigRoute, ...dbProcConfigPopupRoute];

@NgModule({
    imports: [ApicockpitSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DbProcConfigComponent,
        DbProcConfigDetailComponent,
        DbProcConfigUpdateComponent,
        DbProcConfigDeleteDialogComponent,
        DbProcConfigDeletePopupComponent
    ],
    entryComponents: [
        DbProcConfigComponent,
        DbProcConfigUpdateComponent,
        DbProcConfigDeleteDialogComponent,
        DbProcConfigDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApicockpitDbProcConfigModule {}
