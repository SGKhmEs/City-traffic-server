import { BaseEntity } from './../../shared';

export class Car implements BaseEntity {
    constructor(
        public id?: number,
        public qr?: string,
        public carOnRoutes?: BaseEntity[],
        public gps?: BaseEntity[],
    ) {
    }
}
