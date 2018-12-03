import { Moment } from 'moment';
import { IApiConsumerProfile } from 'app/shared/model//api-consumer-profile.model';
import { IApiServiceGroup } from 'app/shared/model//api-service-group.model';

export interface IApiPublisherProfile {
    id?: number;
    name?: string;
    active?: boolean;
    apiBaseUrl?: string;
    apiDocUrl?: string;
    dateCreated?: Moment;
    consumerProfiles?: IApiConsumerProfile[];
    serviceConfigs?: IApiServiceGroup[];
    platformUserId?: number;
}

export class ApiPublisherProfile implements IApiPublisherProfile {
    constructor(
        public id?: number,
        public name?: string,
        public active?: boolean,
        public apiBaseUrl?: string,
        public apiDocUrl?: string,
        public dateCreated?: Moment,
        public consumerProfiles?: IApiConsumerProfile[],
        public serviceConfigs?: IApiServiceGroup[],
        public platformUserId?: number
    ) {
        this.active = this.active || false;
    }
}
