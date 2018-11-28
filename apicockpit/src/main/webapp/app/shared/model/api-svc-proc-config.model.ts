import { Moment } from 'moment';

export const enum ProcessorType {
    DB = 'DB',
    HTTP = 'HTTP',
    GOOVY = 'GOOVY'
}

export interface IApiSvcProcConfig {
    id?: number;
    name?: string;
    description?: string;
    active?: boolean;
    dateCreated?: Moment;
    processorType?: ProcessorType;
    order?: number;
    serviceConfigName?: string;
    serviceConfigId?: number;
}

export class ApiSvcProcConfig implements IApiSvcProcConfig {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public active?: boolean,
        public dateCreated?: Moment,
        public processorType?: ProcessorType,
        public order?: number,
        public serviceConfigName?: string,
        public serviceConfigId?: number
    ) {
        this.active = this.active || false;
    }
}
