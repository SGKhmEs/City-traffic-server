import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { CarOnRoute } from './car-on-route.model';
import { CarOnRouteService } from './car-on-route.service';

@Component({
    selector: 'jhi-car-on-route-detail',
    templateUrl: './car-on-route-detail.component.html'
})
export class CarOnRouteDetailComponent implements OnInit, OnDestroy {

    carOnRoute: CarOnRoute;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private carOnRouteService: CarOnRouteService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCarOnRoutes();
    }

    load(id) {
        this.carOnRouteService.find(id).subscribe((carOnRoute) => {
            this.carOnRoute = carOnRoute;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCarOnRoutes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'carOnRouteListModification',
            (response) => this.load(this.carOnRoute.id)
        );
    }
}
