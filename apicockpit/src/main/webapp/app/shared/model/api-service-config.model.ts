import { Moment } from 'moment';
import { IApiSvcProcConfig } from 'app/shared/model//api-svc-proc-config.model';

export interface IApiServiceConfig {
    id?: number;
    name?: string;
    code?: string;
    description?: string;
    serviceUrl?: string;
    serviceDocUrl?: string;
    active?: boolean;
    dateCreated?: Moment;
    serviceGroupName?: string;
    serviceGroupId?: number;
    actions?: IApiSvcProcConfig[];
}

export class ApiServiceConfig implements IApiServiceConfig {
    constructor(
        public id?: number,
        public name?: string,
        public code?: string,
        public description?: string,
        public serviceUrl?: string,
        public serviceDocUrl?: string,
        public active?: boolean,
        public dateCreated?: Moment,
        public serviceGroupName?: string,
        public serviceGroupId?: number,
        public actions?: IApiSvcProcConfig[]
    ) {
        this.active = this.active || false;
    }
}
