import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SweetAlert2Module } from '@toverux/ngx-sweetalert2';
import { NgSelectModule } from '@ng-select/ng-select';
import { NgSelectizeModule } from 'ng-selectize';

import { ApicockpitSharedModule } from 'app/shared';
import {
    ApiProjectComponent,
    ApiProjectDetailComponent,
    ApiProjectUpdateComponent,
    ApiProjectDeletePopupComponent,
    ApiProjectDeleteDialogComponent,
    apiProjectRoute,
    apiProjectPopupRoute
} from './';

const ENTITY_STATES = [...apiProjectRoute, ...apiProjectPopupRoute];

@NgModule({
    imports: [ApicockpitSharedModule, RouterModule.forChild(ENTITY_STATES), SweetAlert2Module, NgSelectizeModule, NgSelectModule],
    declarations: [
        ApiProjectComponent,
        ApiProjectDetailComponent,
        ApiProjectUpdateComponent,
        ApiProjectDeleteDialogComponent,
        ApiProjectDeletePopupComponent
    ],
    entryComponents: [ApiProjectComponent, ApiProjectUpdateComponent, ApiProjectDeleteDialogComponent, ApiProjectDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApicockpitApiProjectModule {}
