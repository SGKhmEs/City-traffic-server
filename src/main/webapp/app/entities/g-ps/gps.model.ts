import { BaseEntity } from './../../shared';

export class GPS implements BaseEntity {
    constructor(
        public id?: number,
        public time?: any,
        public latitude?: string,
        public longitude?: string,
        public accurancy?: number,
        public speed?: number,
        public carId?: number,
    ) {
    }
}
