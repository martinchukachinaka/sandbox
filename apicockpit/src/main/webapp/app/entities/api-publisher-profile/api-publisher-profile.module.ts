import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApicockpitSharedModule } from 'app/shared';
import {
    ApiPublisherProfileComponent,
    ApiPublisherProfileDetailComponent,
    ApiPublisherProfileUpdateComponent,
    ApiPublisherProfileDeletePopupComponent,
    ApiPublisherProfileDeleteDialogComponent,
    apiPublisherProfileRoute,
    apiPublisherProfilePopupRoute
} from './';

const ENTITY_STATES = [...apiPublisherProfileRoute, ...apiPublisherProfilePopupRoute];

@NgModule({
    imports: [ApicockpitSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ApiPublisherProfileComponent,
        ApiPublisherProfileDetailComponent,
        ApiPublisherProfileUpdateComponent,
        ApiPublisherProfileDeleteDialogComponent,
        ApiPublisherProfileDeletePopupComponent
    ],
    entryComponents: [
        ApiPublisherProfileComponent,
        ApiPublisherProfileUpdateComponent,
        ApiPublisherProfileDeleteDialogComponent,
        ApiPublisherProfileDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApicockpitApiPublisherProfileModule {}
