export class CabPosition {
    constructor(
        public id?: number,
        public qr?: string,
        public time?: string,
        public latitude?: string,
        public longitude?: string,
        public accuracy?: string,
        public speed?: string,
    ) {
    }
}
