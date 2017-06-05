import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CityTrafficServerSharedModule } from '../../shared';
import {
    CabPositionService,
    CabPositionPopupService,
    CabPositionComponent,
    CabPositionDetailComponent,
    CabPositionDialogComponent,
    CabPositionPopupComponent,
    CabPositionDeletePopupComponent,
    CabPositionDeleteDialogComponent,
    cabPositionRoute,
    cabPositionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...cabPositionRoute,
    ...cabPositionPopupRoute,
];

@NgModule({
    imports: [
        CityTrafficServerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CabPositionComponent,
        CabPositionDetailComponent,
        CabPositionDialogComponent,
        CabPositionDeleteDialogComponent,
        CabPositionPopupComponent,
        CabPositionDeletePopupComponent,
    ],
    entryComponents: [
        CabPositionComponent,
        CabPositionDialogComponent,
        CabPositionPopupComponent,
        CabPositionDeleteDialogComponent,
        CabPositionDeletePopupComponent,
    ],
    providers: [
        CabPositionService,
        CabPositionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CityTrafficServerCabPositionModule {}
