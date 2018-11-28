import { Moment } from 'moment';
import { IApiProject } from 'app/shared/model//api-project.model';

export interface IApiConsumerProfile {
    id?: number;
    name?: string;
    active?: boolean;
    dateCreated?: Moment;
    projects?: IApiProject[];
    platformUserId?: number;
    ownerName?: string;
    ownerId?: number;
}

export class ApiConsumerProfile implements IApiConsumerProfile {
    constructor(
        public id?: number,
        public name?: string,
        public active?: boolean,
        public dateCreated?: Moment,
        public projects?: IApiProject[],
        public platformUserId?: number,
        public ownerName?: string,
        public ownerId?: number
    ) {
        this.active = this.active || false;
    }
}
