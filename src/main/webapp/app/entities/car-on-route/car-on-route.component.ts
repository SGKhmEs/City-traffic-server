import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { CarOnRoute } from './car-on-route.model';
import { CarOnRouteService } from './car-on-route.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-car-on-route',
    templateUrl: './car-on-route.component.html'
})
export class CarOnRouteComponent implements OnInit, OnDestroy {
carOnRoutes: CarOnRoute[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private carOnRouteService: CarOnRouteService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.carOnRouteService.query().subscribe(
            (res: ResponseWrapper) => {
                this.carOnRoutes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCarOnRoutes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CarOnRoute) {
        return item.id;
    }
    registerChangeInCarOnRoutes() {
        this.eventSubscriber = this.eventManager.subscribe('carOnRouteListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
