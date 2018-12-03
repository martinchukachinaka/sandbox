import { Moment } from 'moment';

export interface IApiProjectAuthConfig {
    id?: number;
    clientId?: string;
    clientSecret?: string;
    active?: boolean;
    dateCreated?: Moment;
    projectId?: number;
}

export class ApiProjectAuthConfig implements IApiProjectAuthConfig {
    constructor(
        public id?: number,
        public clientId?: string,
        public clientSecret?: string,
        public active?: boolean,
        public dateCreated?: Moment,
        public projectId?: number
    ) {
        this.active = this.active || false;
    }
}
