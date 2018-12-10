import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { ApicockpitSharedLibsModule, ApicockpitSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
    imports: [ApicockpitSharedLibsModule, ApicockpitSharedCommonModule, NgSelectModule],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [JhiLoginModalComponent],
    exports: [ApicockpitSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective, NgSelectModule],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApicockpitSharedModule {}
