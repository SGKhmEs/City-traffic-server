import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CityTrafficServerCarModule } from './car/car.module';
import { CityTrafficServerBusRouteModule } from './bus-route/bus-route.module';
import { CityTrafficServerGPSModule } from './g-ps/gps.module';
import { CityTrafficServerCarOnRouteModule } from './car-on-route/car-on-route.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CityTrafficServerCarModule,
        CityTrafficServerBusRouteModule,
        CityTrafficServerGPSModule,
        CityTrafficServerCarOnRouteModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CityTrafficServerEntityModule {}
