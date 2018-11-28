import { Moment } from 'moment';

export interface IDbProcConfig {
    id?: number;
    name?: string;
    description?: string;
    active?: boolean;
    dateCreated?: Moment;
    pScript?: string;
    configName?: string;
    configId?: number;
}

export class DbProcConfig implements IDbProcConfig {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public active?: boolean,
        public dateCreated?: Moment,
        public pScript?: string,
        public configName?: string,
        public configId?: number
    ) {
        this.active = this.active || false;
    }
}
