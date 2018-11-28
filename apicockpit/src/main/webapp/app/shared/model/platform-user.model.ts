export interface IPlatformUser {
    id?: number;
    phoneNumber?: string;
    userId?: number;
    apiPublisherProfileId?: number;
    apiConsumerProfileId?: number;
}

export class PlatformUser implements IPlatformUser {
    constructor(
        public id?: number,
        public phoneNumber?: string,
        public userId?: number,
        public apiPublisherProfileId?: number,
        public apiConsumerProfileId?: number
    ) {}
}
