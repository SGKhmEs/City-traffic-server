import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CityTrafficServerSharedModule } from '../../shared';
import {
    GPSService,
    GPSPopupService,
    GPSComponent,
    GPSDetailComponent,
    GPSDialogComponent,
    GPSPopupComponent,
    GPSDeletePopupComponent,
    GPSDeleteDialogComponent,
    gPSRoute,
    gPSPopupRoute,
} from './';

const ENTITY_STATES = [
    ...gPSRoute,
    ...gPSPopupRoute,
];

@NgModule({
    imports: [
        CityTrafficServerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GPSComponent,
        GPSDetailComponent,
        GPSDialogComponent,
        GPSDeleteDialogComponent,
        GPSPopupComponent,
        GPSDeletePopupComponent,
    ],
    entryComponents: [
        GPSComponent,
        GPSDialogComponent,
        GPSPopupComponent,
        GPSDeleteDialogComponent,
        GPSDeletePopupComponent,
    ],
    providers: [
        GPSService,
        GPSPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CityTrafficServerGPSModule {}
