import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CityTrafficServerSharedModule } from '../../shared';
import {
    CarOnRouteService,
    CarOnRoutePopupService,
    CarOnRouteComponent,
    CarOnRouteDetailComponent,
    CarOnRouteDialogComponent,
    CarOnRoutePopupComponent,
    CarOnRouteDeletePopupComponent,
    CarOnRouteDeleteDialogComponent,
    carOnRouteRoute,
    carOnRoutePopupRoute,
} from './';

const ENTITY_STATES = [
    ...carOnRouteRoute,
    ...carOnRoutePopupRoute,
];

@NgModule({
    imports: [
        CityTrafficServerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CarOnRouteComponent,
        CarOnRouteDetailComponent,
        CarOnRouteDialogComponent,
        CarOnRouteDeleteDialogComponent,
        CarOnRoutePopupComponent,
        CarOnRouteDeletePopupComponent,
    ],
    entryComponents: [
        CarOnRouteComponent,
        CarOnRouteDialogComponent,
        CarOnRoutePopupComponent,
        CarOnRouteDeleteDialogComponent,
        CarOnRouteDeletePopupComponent,
    ],
    providers: [
        CarOnRouteService,
        CarOnRoutePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CityTrafficServerCarOnRouteModule {}
