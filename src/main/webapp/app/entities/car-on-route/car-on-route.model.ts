import { BaseEntity } from './../../shared';

export class CarOnRoute implements BaseEntity {
    constructor(
        public id?: number,
        public timeLogin?: any,
        public timeLogout?: any,
        public carId?: number,
        public routeId?: number,
    ) {
    }
}
