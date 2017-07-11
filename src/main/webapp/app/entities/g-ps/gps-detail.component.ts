import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { GPS } from './gps.model';
import { GPSService } from './gps.service';

@Component({
    selector: 'jhi-gps-detail',
    templateUrl: './gps-detail.component.html'
})
export class GPSDetailComponent implements OnInit, OnDestroy {

    gPS: GPS;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private gPSService: GPSService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGPS();
    }

    load(id) {
        this.gPSService.find(id).subscribe((gPS) => {
            this.gPS = gPS;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGPS() {
        this.eventSubscriber = this.eventManager.subscribe(
            'gPSListModification',
            (response) => this.load(this.gPS.id)
        );
    }
}
