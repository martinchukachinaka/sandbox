import { IApiServiceConfig } from 'app/shared/model//api-service-config.model';

export interface IApiServiceGroup {
    id?: number;
    name?: string;
    description?: string;
    availableServices?: IApiServiceConfig[];
    ownerName?: string;
    ownerId?: number;
}

export class ApiServiceGroup implements IApiServiceGroup {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public availableServices?: IApiServiceConfig[],
        public ownerName?: string,
        public ownerId?: number
    ) {}
}
