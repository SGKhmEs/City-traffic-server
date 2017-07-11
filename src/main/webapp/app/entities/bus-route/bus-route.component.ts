import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { BusRoute } from './bus-route.model';
import { BusRouteService } from './bus-route.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-bus-route',
    templateUrl: './bus-route.component.html'
})
export class BusRouteComponent implements OnInit, OnDestroy {
busRoutes: BusRoute[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private busRouteService: BusRouteService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.busRouteService.query().subscribe(
            (res: ResponseWrapper) => {
                this.busRoutes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBusRoutes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: BusRoute) {
        return item.id;
    }
    registerChangeInBusRoutes() {
        this.eventSubscriber = this.eventManager.subscribe('busRouteListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
