import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { CityTrafficServerSharedModule, UserRouteAccessService } from './shared';
import { CityTrafficServerHomeModule } from './home/home.module';
import { CityTrafficServerAdminModule } from './admin/admin.module';
import { CityTrafficServerAccountModule } from './account/account.module';
import { CityTrafficServerEntityModule } from './entities/entity.module';

import { LayoutRoutingModule } from './layouts';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        CityTrafficServerSharedModule,
        CityTrafficServerHomeModule,
        CityTrafficServerAdminModule,
        CityTrafficServerAccountModule,
        CityTrafficServerEntityModule
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class CityTrafficServerAppModule {}
