import { BaseEntity } from './../../shared';

export class BusRoute implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public carOnRoutes?: BaseEntity[],
    ) {
    }
}
