import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApicockpitSharedModule } from 'app/shared';
import {
    ApiConsumerProfileComponent,
    ApiConsumerProfileDetailComponent,
    ApiConsumerProfileUpdateComponent,
    ApiConsumerProfileDeletePopupComponent,
    ApiConsumerProfileDeleteDialogComponent,
    apiConsumerProfileRoute,
    apiConsumerProfilePopupRoute
} from './';

const ENTITY_STATES = [...apiConsumerProfileRoute, ...apiConsumerProfilePopupRoute];

@NgModule({
    imports: [ApicockpitSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ApiConsumerProfileComponent,
        ApiConsumerProfileDetailComponent,
        ApiConsumerProfileUpdateComponent,
        ApiConsumerProfileDeleteDialogComponent,
        ApiConsumerProfileDeletePopupComponent
    ],
    entryComponents: [
        ApiConsumerProfileComponent,
        ApiConsumerProfileUpdateComponent,
        ApiConsumerProfileDeleteDialogComponent,
        ApiConsumerProfileDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApicockpitApiConsumerProfileModule {}
